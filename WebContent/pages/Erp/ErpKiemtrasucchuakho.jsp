<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErpKiemtrasucchuakhoList"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% dbutils db = new dbutils(); %> 

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpKiemtrasucchuakhoList obj =(IErpKiemtrasucchuakhoList)session.getAttribute("ktrakhoList");
	String thang = obj.getThang();
	String nam = obj.getNam();
	NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
		
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
	<script type="text/javascript" src="../scripts/jquery.js"></script>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 300px;
			border: 1px solid black;
			padding: 5px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			font-size: 1.2em;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		
  	</style>  	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['ktrakho'].nam.value= "";
	    document.forms['ktrakho'].thang.value= "";
	    document.forms['ktrakho'].spId.value = "";
	    document.forms['ktrakho'].chungloai.value = "";
		document.forms['ktrakho'].submit();
	}

	function submitform()
	{
		document.forms['ktrakho'].action.value= 'search';
		document.forms['ktrakho'].submit();
	}

	function newform()
	{
		document.forms['ktrakho'].action.value= 'new';
		document.forms['ktrakho'].submit();
	}
	
	function runform()
	{
		document.forms['ktrakho'].action.value= 'run';
		document.forms['ktrakho'].submit();
	}

	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back va dau cham, phim Tab
				return;
			}
			return false;
		}
	}
	
	 function readExcel()
	 {
		 document.forms['ktrakho'].action.value='readExcel';
		 document.forms['ktrakho'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['ktrakho'].submit();
	 }
	 
	 function exportExcel()
	 {
		 document.forms['ktrakho'].action.value='exportExcel';
	     document.forms['ktrakho'].submit();
	 }
	
	function THONGBAO()
	{
		if(document.getElementById("msg").value != '' )
		{
			alert(document.getElementById("msg").value);
		}
	}
	
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ktrakho" method="post" action="../../ErpKiemtrasucchuakhoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="kbsxId" value="" >
<input type="hidden" name="Id" value="" >
<input type="hidden" name="soluongsx" value="" >
<input type="hidden" name="msg" id="msg" value="<%= obj.getMsg() %>" >
<script type="text/javascript">THONGBAO();</script>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Kiểm tra sức chứa kho </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="250%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                                                      
                    		 <TR>
		                        <TD class="plainlabel" width="5%">Năm</TD>
		                        <TD class="plainlabel" align = "left">
		                        	<SELECT name = "nam"  style="width: 100px;" onChange = "submitform();">
		                        		<%
										int year =  Integer.parseInt(obj.getDateTime().substring(0, 4)) - 1;                       		
		                        			                        		
		                        		for(int i = 0; i <= 2; i++){		                        				
		                        			
		                        			if(obj.getNam().equals("" + year) ){
		                        		%>
			                        		<OPTION value = <%= year  %> selected> <%= year %></OPTION>
			                        	
			                        	<% 	}else{ %>
		                        			
		                        			<OPTION value = <%= year  %> > <%= year %></OPTION>		                        			
		                        		<%	}
		                        			year = year + 1;
		                        		}%>
		                        	</SELECT>
		                        	
		                        </TD>
                    		</TR>	
                    		<TR>
                        		<TD class="plainlabel">Tháng</TD>
                        		<TD class="plainlabel">
		                        	<SELECT name="thang" style="width: 100px;" onChange = "submitform();">
											<option value = '0'>&nbsp;</option>
		                        <%	int t = 0;
		                        
		                        	if(obj.getThang().length() > 0 ) 
		                        		t = Integer.parseInt(obj.getThang());
		                        			                        		                        	
		                        	for(int m = 1; m <= 12; m++){
		                        		if(t == m){ %>
		                        			<option value="<%= m %>" selected="selected"><%="Tháng " + m %></option>
		                       <% 		}else{ %>
		                					<option value="<%= m %>" ><%="Tháng " + m %></option>
		                       <% 		}
		                        	}
		                        %>
		                        
		                        	</SELECT>
                        		
                        		</TD>

							</TR> 
         					 <TR>
                    				<TD class="plainlabel" valign="middle">Kho</TD>
                     				<TD class="plainlabel" valign="middle" >
                        				<select  id="khoId" name="khoId" style="width: 250px;" onChange = "submitform();">
                          					<option value=""></option>
                          			<%
                          		ResultSet khoRs = obj.getKhoRs();
	                      		if(khoRs  != null)
	                       		{
	                      			khoRs.beforeFirst();
	                       			while(khoRs.next())
	                       			{  
		                       			if( khoRs.getString("ID").equals(obj.getKhoId())){ %>
	                        				<option value="<%= khoRs .getString("ID") %>" selected="selected" ><%= khoRs.getString("KHO") %></option>
	                        			<%}else 
		                        			{ %>
		                        			<option value="<%= khoRs .getString("ID") %>" ><%= khoRs.getString("KHO") %></option>
		                        		 <% } 
	                        		} 
	                       			
	                        	}
	                        	%>
                            			</select>
                      				</TD>                        
                			</TR>

                             <tr class="plainlabel"> 
                             	<td  colspan = 3 > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td>
                             	                             	 
                             </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Kiểm tra sức chứa kho &nbsp;&nbsp;	      
                            </LEGEND>
                            <TABLE  width="100%" border="0" cellspacing="2" cellpadding="0">
                                <TR class="tbheader">
                                	<TH width="5%" align = "center" > Kho</TH>
						 
				<%		int n = obj.getSongay();
				
						for (int i = 1; i <= n; i++){ 
							String ngay = (obj.getNam() + "-" + (obj.getThang().length() == 1?"0" + obj.getThang():obj.getThang())+ "-" + (("" + i).length() == 1?"0" + i:"" + i));	
						%>	
							
									<TH><%=  ngay  %></TH>
				 
				<%		} %>
								</TR>
										                                	
                <%    
                		ResultSet khoRs_2 = obj.getKho();
                        if(khoRs_2 != null){ 
                        	int m = 0;
                        	
                         	while(khoRs_2.next()){		
               					if(m % 2 == 0){
               %>
               					<TR class = "tbdarkrow" >
               <%
               					}else{
               %>				
               					<TR class = "tblightrow" >
               <%				} %>
								
									<TD><%= khoRs_2.getString("KHO") %></TD>
			   <%				for (int i = 1; i <= n; i++){ 
				   					String ngay = (obj.getNam() + "-" + (obj.getThang().length() == 1?"0" + obj.getThang():obj.getThang())+ "-" + (("" + i).length() == 1?"0" + i:"" + i));			   			   
			   %> 
									<TD align = "center">
			   <%					ResultSet rs = obj.getSucchuakho_Ngay(ngay, khoRs_2.getString("ID")); 
			   						if(rs != null){
			   							boolean flag = false;
			   %>					
										<TABLE border = "0" cellpadding = "10" cellspacing = "2">
				<%						
										while(rs.next()){
											flag = true;
											long num = rs.getLong("SUCCHUA")*(80 + ( 1 + (int)(Math.random() * 35) ))/100 ;
											if(num > rs.getLong("SUCCHUA")){
				%>						
											<TR bgcolor = #DC143C style = "color:white">
											
				<%							}else{ %>
											<TR bgcolor = "#C5E8CD">
				<%							}
											String tip = "Tồn kho (m3): ";
											
											tip += "Nhập (m3): ";
											tip += "Xuất (m3): ";
				%>											
												<TD align = "center" onMouseover='ddrivetip("<%= tip %>" , 170)' onMouseout="hideddrivetip()">
													Tối đa (m3):  <%= rs.getLong("SUCCHUA") %> <BR><BR>
													Dự toán (m3): <%= num %>
												</TD>				
											</TR>
										
				<%						} 
				
										rs.close();
										
										%>
																	
				<%							
										
					%>						
										</TABLE>
									
				<%					} %>
									</TD>

			  <%				} %>	
								</TR>
                <%         	
                			m++;
               			}
                        khoRs.close(); 	
                       	}
						%>									
								</TR>
                                    								
		                    </TABLE>
                    	</FIELDSET>
                    </TD>
                </TR>

        </TABLE>
        </TD>
    </TR>
</TABLE>
</form>

</BODY>
</html>
<% 	
	try
    {
		if(khoRs != null)
			khoRs.close();
	 
		
		if(db != null)
			db.shutDown();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
		session.setAttribute("ktrakhoList", null) ;  
		
		
	}
	catch (SQLException e) {} %>
<%}%>