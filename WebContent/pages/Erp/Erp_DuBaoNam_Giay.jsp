<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		IDubaokinhdoanhNam_Giay_List obj = (IDubaokinhdoanhNam_Giay_List)session.getAttribute("obj"); 
		ResultSet dvkd = (ResultSet) obj.getDvkd(); 
		ResultSet dubaoList = (ResultSet)obj.getDubaoList(); 
		ResultSet dubaocopy = (ResultSet)obj.getDubaoCopy(); 
		
		obj.setNextSplittings(); 
    	ResultSet nsList = obj.getNsList();
    	int[] quyen = new  int[5];
		quyen = util.Getquyen("DubaoNam_GiaySvl","",userId);
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
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>    
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
	    document.forms["dbnamForm"].submit();
	 }
	 
	 function newform()
	 {   
		document.forms["dbnamForm"].action.value = "Taomoi";
	    document.forms["dbnamForm"].submit();
	 }
	 
	 function clearform()
	 {   
	    document.forms["dbnamForm"].diengiai.value = "";
	    document.forms["dbnamForm"].nsId.value = "";
	    document.forms["dbnamForm"].dvkdId.value = "";
	    document.forms["dbnamForm"].submit();
	 }
	 
	 function copyform()
	 {
	 	document.forms['dbnamForm'].action.value= 'copy';
	 	document.forms['dbnamForm'].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["dbnamForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["dbnamForm"].msg.value);
	 }
	</SCRIPT>
</head>
<body>
<form name="dbnamForm" method="post" action="../../DubaoNam_GiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý ngân sách > Dự báo kinh doanh
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
                                   id="diengiai" name="diengiai" value="<%= obj.getDiengiai() %>" maxlength="10" onChange = "submitform();"/>
                        </TD>
                    </TR>								         
                	<TR>
	                    <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	                    <TD class="plainlabel">
	                       	<SELECT name = "nsId"  style="width: 300px;" onChange = "submitform();">
	                       			<OPTION value = "" >&nbsp;</OPTION>
		                        <%
		                        if(nsList != null){
									while(nsList.next()){
										if(nsList.getString("nsId").equals(obj.getNsId())){
		                        		%>
			                        		<OPTION value = <%= nsList.getString("nsId")  %> selected> <%= nsList.getString("diengiai") %></OPTION>
			                        	
			                        	<%}else{ 	 %>
		                        			
		                        			<OPTION value = <%= nsList.getString("nsId")  %> > <%= nsList.getString("diengiai") %></OPTION>		                        			
		                        		<%	
		                        		}
		                       		}
		                       }%>
	                       	</SELECT>
		                       	
	                      </TD>
                   	</TR>	
                                                        
                     <TR >
                        <TD class="plainlabel" valign="middle">Đơn vị kinh doanh </TD>
                        <TD  valign="middle" class="plainlabel">
                           <select name="dvkdId" onChange = "submitform();">
                           		<option value=""> </option>
                           		
							<% while(dvkd.next()){ 
								
								if (obj.getDvkdId().equals(dvkd.getString("dvkdId"))){%>
								  	<option value=<%= dvkd.getString("dvkdId") %> selected><%= dvkd.getString("dvkd") %></option>
							<%	}else{ %>
								  	<option value=<%= dvkd.getString("dvkdId") %> ><%= dvkd.getString("dvkd") %></option>
							<%	} 
							 }
								%>
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
            	<legend><span class="legendtitle">Dự báo kinh doanh &nbsp;</span>&nbsp;&nbsp;
            	
						<%if(quyen[0]!=0){ %>
                			<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/add.png" alt="">Tạo mới </a>
                        <%} %>
                        
                        &nbsp;&nbsp;&nbsp;&nbsp;
    					<%if(quyen[0]!=0){ %>
					 	
						<a class="button3" href="" id="copyId"  rel= "copy">
	           	 			&nbsp; <img style="top: -4px;" src="../images/New.png" alt="">Sao chép</a> </a>
	           	 		
                          	<DIV id="copy" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
				                 background-color: white; width: 300px; height:60px; overflow:none; padding: 2px;">
				                 <TABLE width="100%" align="center">
				                     <TR class="tbheader">
										<TD>
											<SELECT NAME  = "copyId" style="width:295px;">
												<OPTION VALUE = "">&nbsp</OPTION>
											<%if(dubaocopy != null) {
												while(dubaocopy.next()){
											
											%>		
													<OPTION VALUE = "<%= dubaocopy.getString("DBID") %>" > <%=  dubaocopy.getString("DIENGIAI") + " - "  + dubaocopy.getString("DVKD") + " " %></OPTION>
											
											<%	}
												dubaocopy.close();
											} %>
											</SELECT>
										</TD>	
									 </TR>
			                     </TABLE>
				                    					
				                 <div align="left" class="legendtitle">
				                 	<label  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				                     								
				                 	<a href="javascript:dropdowncontent.hidediv('copy')" ></a>
				                 </div>
				                 
				                <div align="right">
									<label style="color: red"></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
									href="javascript:copyform()">Thực hiện</a>
								</div>
				                 
				                 <script type="text/javascript">
										dropdowncontent.init('copyId', "right-bottom", 300, "click");
								 </script>
				            </DIV>                        
						<%} %>
						
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã số</TH>
	                    <TH align="center">Năm dự báo</TH>
	                    <TH align="center">Diễn giải</TH>
	                    <TH align="center">Đơn vị kinh doanh</TH>
	                    <TH align="center">Kênh bán hàng</TH>
	                    <TH align="center">Ngân sách</TH>
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
		                    <TD align="center"><%= dubaoList.getString("NAM") %></TD>
		                    <TD align="left"><%= dubaoList.getString("DIENGIAI") %></TD>
		                    <TD align="left"><%= dubaoList.getString("DVKD") %></TD>		                    
		                    <TD align="left"><%= dubaoList.getString("KBH") %></TD>	
		                    <TD  align="left">
		                    	<%= dubaoList.getString("NGANSACH")%>
		                    </TD>   									                                    
         					<TD align="center"><%= dubaoList.getString("NGAYSUA") %></TD>
							<TD align="center"><%= dubaoList.getString("TENNVS") %></TD>
									
		                   <TD align="center"> 
		                   
									<%if(quyen[2]!=0){ %>
                                <A href = "../../DubaoNam_Giay_UpdateSvl?userId=<%=userId%>&update=<%=dubaoList .getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                
							<%if(quyen[1]!=0){ %>
                                <A href = "../../DubaoNam_GiaySvl?userId=<%=userId%>&delete=<%=dubaoList .getString("PK_SEQ") %>"><IMG src="../images/Delete20.png" alt="Xóa" title="Xóa" border=0></A>&nbsp;
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
	if(dubaoList!=null){
		dubaoList.close();
	}
	if(dvkd!=null){
		dvkd.close();
	}
	if(nsList!=null){
		nsList.close();
	}
	
	session.setAttribute("obj", null) ;  
	obj.close();
}catch(Exception er){
	
}
	} %>
</html>