<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.util.Calendar"%>
<%@ page  import = "geso.traphaco.center.beans.nhomfocus.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% INhomfocusList obj = (INhomfocusList)session.getAttribute("obj"); %>
<% ResultSet kbhList = (ResultSet) obj.getKenhbanhangList(); %>
<% ResultSet dvkdList = (ResultSet) obj.getDvkdList(); %>
<% ResultSet nhomfocusList = (ResultSet)obj.getNhomfocusList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
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
		 document.forms["nhomskuForm"].action.value='';
	    document.forms["nhomskuForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["nhomskuForm"].action.value = "Tao moi";
	    document.forms["nhomskuForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["nhomskuForm"].thang.value = "0";
	    document.forms["nhomskuForm"].nam.value = "0";
	    document.forms["nhomskuForm"].donvikinhdoanh.value = "";
	    document.forms["nhomskuForm"].kenhbanhang.value = "";
	    document.forms["nhomskuForm"].trangthai.value = "";
	    document.forms["nhomskuForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["nhomskuForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["nhomskuForm"].msg.value);
	 }
	 
	 function xuatExcel()
	 {
	 	document.forms['nhomskuForm'].action.value= 'toExcel';
	 	document.forms['nhomskuForm'].submit();
	 }
	 
	</SCRIPT>
</head>
<body>
<form name="nhomskuForm" method="post" action="../../NhomFocusSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<%-- <input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" > --%>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Dữ liệu nều > Sản phẩm > Nhóm thưởng
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
                         <TD width="15%" class="plainlabel" >Tháng &nbsp;&nbsp;  <FONT class="erroralert"> </FONT></TD>
						<TD class="plainlabel">
							<select name="thang" style="width :100px">
							<option value='0'> </option>  
							<%
							int k=1;
							int thang=-1;
							if(obj.getThang().length()!=0)
								thang=Integer.parseInt(obj.getThang());
							for(k=1;k<=12;k++){
							  if(k==thang){
							  
							%>
							<option value=<%=k %> selected="selected" > <%=k %></option> 
							<%
							  }else{
							 %>
							<option value=<%=k %> ><%=k %></option> 
							<%
							  }
							  }
							%>
							</select>
						</TD>
                         </TR>
                         <TR>
	                         <TD width="15%" class="plainlabel" >Năm &nbsp;&nbsp;  <FONT class="erroralert"> </FONT></TD>
							<TD class="plainlabel">
								<select name="nam"  style="width :100px">
								<option value='0'> </option>  
								<%
								Calendar cal=Calendar.getInstance(); 
								int nam=-1;
								if(obj.getNam().length()!=0)
									nam=Integer.parseInt(obj.getNam());
								int year_=cal.get(Calendar.YEAR);
								for(int n=2008;n<year_+3;n++){
								  if(n== nam){									  
								%>
								<option value=<%=n %> selected="selected" > <%=n %></option> 
								<%
								  }else{
								 %>
								<option value=<%=n %> ><%=n %></option> 
								<%
								  }
								  }
								%>
								</select>
							</TD>
                        </TR >
                    <TR>
                        <TD class="plainlabel" valign="middle" >Đơn vị kinh doanh</TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="donvikinhdoanh">
                            	<option value=""></option>
                            	<%
	                        		if(dvkdList  != null)
	                        		{
	                        			while(dvkdList .next())
	                        			{  
	                        			if( dvkdList.getString("pk_seq").equals(obj.getDvkd())){ %>
	                        				<option value="<%= dvkdList .getString("pk_seq") %>" selected="selected" ><%= dvkdList.getString("DONVIKINHDOANH") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvkdList .getString("pk_seq") %>" ><%= dvkdList.getString("DONVIKINHDOANH") %></option>
	                        		 <% } } dvkdList .close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>
                     <TR>
                        <TD class="plainlabel" valign="middle" >Kênh bán hàng</TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="kenhbanhang">
                            	<option value=""></option>
                            	<%
	                        		if(kbhList  != null)
	                        		{
	                        			while(kbhList.next())
	                        			{  
	                        			if( kbhList.getString("pk_seq").equals(obj.getKenhbanhang())){ %>
	                        				<option value="<%= kbhList.getString("pk_seq") %>" selected="selected" ><%= kbhList.getString("diengiai") %></option>
	                        			<%}else { %>
	                        				<option value="<%= kbhList.getString("pk_seq") %>" ><%= kbhList.getString("diengiai") %></option>
	                        		 <% } } kbhList .close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
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
                        <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:submitform();"><img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform();"><img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:xuatExcel();"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Nhóm thưởng</span>&nbsp;&nbsp;
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã số</TH>
	                     <TH align="center">Diễn giải</TH>
	                    <TH align="center">Tháng</TH>
	                    <TH align="center">Năm</TH>
	                    <TH align="center">Đối tượng</TH>
	                    <TH align="center">Đơn vị kinh doanh</TH>
	                    <TH align="center">Kênh bán hàng</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhomfocusList != null)
                 		{
                 			int m = 0;
                 			while(nhomfocusList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= nhomfocusList.getString("PK_SEQ") %></TD>
		                    <TD align="center"><%= nhomfocusList.getString("DienGiai") %></TD>
		                    <TD align="center"><%= nhomfocusList.getString("THANG") %></TD>
		                    <TD align="center"><%= nhomfocusList.getString("NAM") %></TD>	
		                    	 <TD align="center">
		                    	<%
		                    		String doituong = "";
		                    		String tt = nhomfocusList.getString("DOITUONG");
		                    		if(tt.trim().equals("1"))
		                    			doituong = "SS";
		                    		if(tt.trim().equals("2"))
	                    			{
	                    				doituong = "ASM";
	                    			}
	                    			 if(tt.trim().equals("3"))
	                    			{

	                    				doituong = "RSM";
                    				}else if (tt.trim().equals("0")) {
                    					doituong = "SR";
                    				}
		                    			

		                    	%>
		                    	<%= doituong %>
		                    </TD>
		                    <TD align="center"><%= nhomfocusList.getString("tendvkd") %></TD>
		                    <TD align="center"><%= nhomfocusList.getString("tenkbh") %></TD>
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String ttx = nhomfocusList.getString("trangthai");
		                    		if(ttx.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(ttx.equals("1"))
		                    				trangthai = "Đã chốt";
		                    			else if(ttx.equals("2"))
		                    			{

			                    				trangthai = "Đã hủy";
		                    			}
		                    			else if(ttx.equals("3"))
		                    			{

		                    				trangthai = "Hoàn tất";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   				   									                                    
					     	<TD align="center"><%= nhomfocusList.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhomfocusList.getString("TENNV") %></TD>
         					<TD align="center"><%= nhomfocusList.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhomfocusList.getString("TENNVS") %></TD>
									
		                    <TD align="center"> 
		                    <A href = "../../NhomFocusUpdateSvl?userId=<%=userId%>&copy=<%=nhomfocusList.getString("PK_SEQ") %>"><IMG src="../images/convert.gif" width="20" height="20" alt="Copy" title="Copy" border=0></A>&nbsp;
		                    <% if(ttx.equals("0")){ %>
		                    	
                                <A href = "../../NhomFocusUpdateSvl?userId=<%=userId%>&update=<%=nhomfocusList.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <A href = "../../NhomFocusSvl?userId=<%=userId%>&chot=<%= nhomfocusList.getString("PK_SEQ") %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0  onclick="if(!confirm('Bạn có muốn chốt nhóm thưởng này?')) return false;"></A>&nbsp;
                                <A href = "../../NhomFocusSvl?userId=<%=userId%>&delete=<%= nhomfocusList.getString("PK_SEQ") %>&poId=<%= nhomfocusList.getString("PK_SEQ") %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa nhóm focus này?')) return false;"></A>									
		                    <%}else{ if(ttx.equals("1")){ %>
		                    	<A href = "../../NhomFocusUpdateSvl?userId=<%=userId%>&display=<%= nhomfocusList.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    	<A href = "../../NhomFocusSvl?userId=<%=userId%>&huychot=<%= nhomfocusList.getString("PK_SEQ") %>"><img src="../images/unChot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0  onclick="if(!confirm('Bạn có muốn huỷ chốt nhóm thưởng này?')) return false;"></A>&nbsp;	
		                    <%} else { %>
		                    	<A href = "../../NhomFocusUpdateSvl?userId=<%=userId%>&display=<%= nhomfocusList.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    	<% }
		                    } %>
		                    </TD>
		                </TR>
                     <% m++; } nhomfocusList.close(); } %>
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
</html>