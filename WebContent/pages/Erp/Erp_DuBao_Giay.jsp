<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.dubaokinhdoanh.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% IDubaokinhdoanh_Giay_List obj = (IDubaokinhdoanh_Giay_List)session.getAttribute("obj"); %>
<% ResultSet khoList = (ResultSet) obj.getKhoList(); %>
<% ResultSet phuongphapList = (ResultSet)obj.getPhuongphapList(); %>
<% ResultSet dubaoList = (ResultSet)obj.getDubaoList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
   String thongBaoXoa = (String) session.getAttribute("thongbao");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
    	obj.setNextSplittings(); 
    
    	int[] quyen = new  int[5];
	 	quyen = util.Getquyen("DuBao_GiaySvl","&loaiNPP=0&isKH=0",userId);
		
		ResultSet kho = obj.getKhoList();

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
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
	    document.forms["dbkdForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["dbkdForm"].action.value = "Taomoi";
	    document.forms["dbkdForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["dbkdForm"].diengiai.value = "";
	    document.forms["dbkdForm"].dvkdId.value = "";
	    document.forms["dbkdForm"].ngaydubao.value = "";
	    document.forms["dbkdForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["dbkdForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["dbkdForm"].msg.value);
	 }
	</SCRIPT>

</head>
<body>
<form name="dbkdForm" method="post" action="../../DuBao_GiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Dự báo kinh doanh
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
                        <TD class="plainlabel" width="15%">Diễn giải</TD>
                        <TD class="plainlabel">
                            <input type="text"
                                   id="diengiai" name="diengiai" value="<%= obj.getDiengiai() %>"  onChange = "submitform();"/>
                        </TD>
                    </TR>								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Ngày dự báo</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="ngaydubao" name="ngaydubao" value="<%= obj.getNgaydubao() %>" maxlength="10" onChange = "submitform();"/>
                        </TD>
                    </TR>
                    
                    <TR style="display: none">
                        <TD class="plainlabel" valign="middle" >Phương pháp</TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="phuongphap">
                            	<option value=""></option>
                            	<%
	                        		if(phuongphapList  != null)
	                        		{
	                        			while(phuongphapList.next())
	                        			{  
	                        			if( phuongphapList.getString("pk_seq").equals(obj.getPhuongphap())){ %>
	                        				<option value="<%= phuongphapList .getString("pk_seq") %>" selected="selected" ><%= phuongphapList.getString("tenphuongphap") %></option>
	                        			<%}else 
		                        			{ %>
		                        				<option value="<%= phuongphapList .getString("pk_seq") %>" ><%= phuongphapList.getString("tenphuongphap") %></option>
		                        		 <% } 
	                        			} phuongphapList .close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                     <TR style="display: none">
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD  valign="middle">
                           <select name="trangthai" >
                           		<option value=""> </option>
								<% if (obj.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2" >Đã hủy</option>
								  
								<%}else if(obj.getTrangthai().equals("0")) {%>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" >Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
							 	<option value="2" selected>Đã hủy</option>
							  	<option value="0" >Chưa chốt</option>
							  	<option value="1" >Đã chốt</option>
								<%} else  {%>
							 	<option value="0">Chưa chốt</option>
							  	<option value="1" >Đã chốt</option>
							  	<option value="2" >Đã hủy</option>
							<%} %>
                           </select>
                        </TD>                        
                    </TR>    
                    <tr>
                        <td colspan="2" class="plainlabel"><a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Dự báo kinh doanh&nbsp;</span>&nbsp;&nbsp;
            	
				<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã số</TH>
	                    <TH align="center">Ngày dự báo</TH>
	                    <TH align="center">Diễn giải</TH>
	                    	                    
	                    <TH align="center">Hiệu lực</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(dubaoList != null)
                 		{
                 			int m = 0;
                 			while(dubaoList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= dubaoList.getString("PK_SEQ") %></TD>
		                    <TD align="center"><%= dubaoList.getString("NGAYDUBAO") %></TD>
		                    <TD align="left"><%= dubaoList.getString("DIENGIAI") %></TD>
		                    		                    		                   
		                    <% if(dubaoList.getString("HIEULUC").equals("1")){ %>
		                    	<TD align="center">Có</TD>
		                    <%}else{ %>
		                    	<TD align="center">Không</TD>
		                    <%} %>
		                    
		                    <TD style="display: none" align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = dubaoList.getString("trangthai");
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				trangthai = "Đã chốt";
		                    			else if(tt.equals("2"))
		                    			{

			                    				trangthai = "Đã hủy";
		                    			}
		                    			else if(tt.equals("3"))
		                    			{

		                    				trangthai = "Hoàn tất";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= dubaoList.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= dubaoList.getString("TENNV") %></TD>
         					<TD align="center"><%= dubaoList.getString("NGAYSUA") %></TD>
							<TD align="center"><%= dubaoList.getString("TENNVS") %></TD>
									
		                   <TD align="center"> 
		                   
									<%if(quyen[2]!=0){ %>
                                <A href = "../../DuBao_Giay_UpdateSvl?userId=<%=userId%>&update=<%=dubaoList .getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                
							<%if(quyen[1]!=0){ %>
                                <A href = "../../DuBao_GiaySvl?userId=<%=userId%>&delete=<%=dubaoList .getString("PK_SEQ") %>"><IMG src="../images/Delete20.png" alt="Xóa" title="Xóa" border=0></A>&nbsp;
                                <%} %>
		                    </TD>
		                </TR>
                     <% m++; } dubaoList.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
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
<%
try{
	if (kho != null)
		kho.close();
	if(dubaoList!=null){
		dubaoList.close();
	}
	if(phuongphapList!=null){
		phuongphapList.close();
	}
	if(phuongphapList!=null){
		phuongphapList.close();
	}
	obj.close();
}catch(Exception er){
	
}
	} %>
	
<%if(thongBaoXoa!=null) {%>
<script type="text/javascript">
var r=confirm("<%= thongBaoXoa%>");
if (r==true)
  {	
	document.forms["dbkdForm"].action.value = "DeleteOld";
    document.forms["dbkdForm"].submit();
  }
else
  {
	alert("Cancel");
  } 
</script>
<%} %>

</html>
<%
try{
	if (khoList != null)
		khoList.close();
}catch (Exception ex)
{
	ex.printStackTrace();
}finally{
	if (obj != null)
	{
		obj.close();
		obj = null;
	}
	session.setAttribute("obj", null) ; 
	session.setAttribute("thongbao", null) ;
}
%>