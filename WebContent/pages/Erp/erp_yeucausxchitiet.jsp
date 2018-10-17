<%@page import="java.sql.ResultSet"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="geso.traphaco.erp.beans.erp_yeucausx.IYeuCauSX"%>
<%@page import="geso.traphaco.erp.beans.erp_yeucausx.IYeuCauSXList"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
	IYeuCauSXList YCSX = (IYeuCauSXList)session.getAttribute("yc");
	ResultSet clRs  = (ResultSet) YCSX.getChungloaiRS();
	ResultSet data = YCSX.getData();
	String thang = YCSX.getThang();
	String nam = YCSX.getNam();
	
	String t1 = "", t2 = "", t3 = "", n1 = "", n2 = "", n3 = "";
	t1 = thang;
	n1 = nam;

	
	if(t1.equals("12")){
		t2 = "1";
		n2 = "" + (Integer.parseInt(n1) + 1);
	}else{
		t2 = "" + (Integer.parseInt(t1) + 1);
		n2 = n1;
	}
	
	if(t2.equals("12")){
		t3 = "1";
		n3 = "" + (Integer.parseInt(n2) + 1);
	}else{
		t3 = "" + (Integer.parseInt(t2) + 1);
		n3 = n2;
	}
	
	%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yeu Cau SX New</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
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
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();
	})
</script>

<script>

//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");

});
</script>

<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
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
		
		function submitform()
	    {
	    	document.forms['YCSXFrom'].submit();
	    }
		
 </script>


</head>

<body>
<form name="YCSXFrom" method="post" action="../../YeuCauSvl">
<input type="hidden" name="userId" value="<%= userId %>" >

<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
				<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng &gt; &nbsp;Lập kế hoạch &gt; Yêu cầu cung ứng
						</TD>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					</tr>
					</table>
					</td>
				</tr>
			</table>
				
			<table  width="100%" cellspacing="0" cellpadding="6">

					<tr>
						<TD align="left" colspan="4" class="legendtitle">
						<fieldset>
						<LEGEND class="legendtitle">Tiêu chí chọn lọc
						</LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding="6">
               				<TR>
		                    	<TD class="plainlabel" width="15%">Trung tâm phân phối</TD>
		                    	<TD class="plainlabel">
		                        	<SELECT name = "khoId"  style="width: 200px;" onChange = "submitform();">
		                        			<OPTION value = "" selected>Tổng cộng </OPTION>
		                        		
		                        		<% if(YCSX.getKhoId().equals("100013")){ %>		                        		
			                        		<OPTION value = "100013" selected>Kho phân phối miền Nam </OPTION>
			                        		<OPTION value = "100014" >Kho phân phối miền Trung </OPTION>
		                        			<OPTION value = "100012" >Kho phân phối miền Bắc </OPTION>		                        			
		                        		<% }else if(YCSX.getKhoId().equals("100014")){ %>
			                        		<OPTION value = "100013" >Kho phân phối miền Nam </OPTION>
			                        		<OPTION value = "100014" selected>Kho phân phối miền Trung </OPTION>
		                        			<OPTION value = "100012" >Kho phân phối miền Bắc </OPTION>		                        			
		                        		<% }else if(YCSX.getKhoId().equals("100012")){ %>
			                        		<OPTION value = "100013" >Kho phân phối miền Nam </OPTION>
			                        		<OPTION value = "100014" >Kho phân phối miền Trung </OPTION>
		                        			<OPTION value = "100012" selected>Kho phân phối miền Bắc </OPTION>		                        																									                        
										<% }else{ %>
			                        		<OPTION value = "100013" >Kho phân phối miền Nam </OPTION>
			                        		<OPTION value = "100014" >Kho phân phối miền Trung </OPTION>
		                        			<OPTION value = "100012" >Kho phân phối miền Bắc </OPTION>
										
										<%}%>
								
		                        	</SELECT>
		                        	
		                    	</TD>
                   			</TR>	

               				<TR>
                    			<TD class="plainlabel" valign="middle" >Loại</TD>
                   			 	<TD class="plainlabel" valign="middle" colspan = "3">
                            	<select id="loai"  name="loai" onChange="submitform();">
                            		<option value=""></option>
                            	<% if (YCSX.getLoai().equals("1")){%>
								  	<option value="1" selected>Yêu cầu tháng</option>
								  	<option value="2">Yêu cầu tuần</option>
								  								  
								<%}else if(YCSX.getLoai().equals("2")) {%>
								  	<option value="1" >Yêu cầu tháng</option>
								  	<option value="2" selected>Yêu cầu tuần</option>
								<%}else {%>
								  	<option value="1" selected>Yêu cầu tháng</option>
								  	<option value="2">Yêu cầu tuần</option>
								<%} %>
                            	</select>
                    			</TD>                          
                			</TR>
               				<TR>
		                    	<TD class="plainlabel" width="15%">Năm</TD>
		                    	<TD class="plainlabel">
		                        	<SELECT name = "nam"  style="width: 100px;" onChange = "submitform();">
		                        		<%
		                        		int year = Integer.parseInt(YCSX.getDateTime().substring(0, 4));
		                        		for(int i = year; i <= year + 5; i++){		                        				
		                        			if(YCSX.getNam().equals("" + i) ){
		                        		%>
			                        		<OPTION value = <%= i  %> selected> <%= i %></OPTION>
			                        	
			                        	<% 	}else{ %>
		                        			
		                        			<OPTION value = <%= i  %> > <%= i %></OPTION>		                        			
		                        		<%	}
		                        		}%>
		                        

		                        	</SELECT>
		                        	
		                    	</TD>
                   			</TR>	
                   			<TR>
                        		<TD class="plainlabel" width="15%">Từ tháng</TD>
                        		<TD class="plainlabel">
		                        	<SELECT name="thang"  style="width: 100px;" onChange = "submitform();">

		                        <%	
		                        	
		                        	String th = "";
		                        	int month = 0;
//		                        	if((YCSX.getDateTime()).substring(0, 4).equals(nam)){
		                        			                        		
			                        	for(int m = 1 ; m <= 12; m++){
			                        		if( m < 10){
			                        			th = "0" + m;
			                        		}else{
			                        			th = "" + m;
			                        		}
			                        	
			                        		if(thang.equals(th)){ %>
			                        			<option value="<%=th%>" selected="selected"><%="Tháng " + th%></option>
			                       <% 		}else{ %>
			                					<option value="<%=th%>" ><%="Tháng " + th%></option>
			                       <% 		}
			                        	}
			                       
		                        		
//		                        	}else{
		                        		
//		                        		for(int m = 1; m <= 12; m++){
//		                        			if( m < 10){
//		                        				th = "0" + m;
//		                        			}else{
//		                        				th = "" + m;
//		                        			}
		                        	
//		                        			if(thang.equals("" + m)){ %>

		                       <%// 			}else{ %>

		                       <%// 			}
		                         //	}
//		                        	}
		                        %>
		                        
		                        	</SELECT>
                        		
                        		</TD>

							</tr> 

         					 <TR>
                    				<TD class="plainlabel" valign="middle">Chủng loại</TD>
                     				<TD class="plainlabel" valign="middle" colspan = 3>
                        				<select  id="chungloai" name="chungloai" onChange = "submitform();">
                          					<option value=""></option>
                          			<%
	                      		if(clRs  != null)
	                       		{
	                       			while(clRs.next())
	                       			{  
		                       			if( clRs.getString("pk_seq").equals(YCSX.getClId())){ %>
	                        				<option value="<%= clRs .getString("pk_seq") %>" selected="selected" ><%= clRs.getString("ten") %></option>
	                        			<%}else 
		                        			{ %>
		                        			<option value="<%= clRs .getString("pk_seq") %>" ><%= clRs.getString("ten") %></option>
		                        		 <% } 
	                        		} 
	                       			clRs .close();
	                        	}
	                        	%>
                            			</select>
                      				</TD>                        
                			</TR>
		                    <tr>
        		                <td colspan="2" class="plainlabel">
                	            <a class="button2" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Hiển thị</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    	    	</td>
                    		</tr>        					
							
						</TABLE>
             
					</fieldset>
				</TD>
			</TR>
			<TR>
				<TD>	
					<fieldset>
					<LEGEND class="legendtitle">Yêu cầu cung ứng</LEGEND>
								
					<TABLE  width="100%" cellspacing="1" cellpadding="6">
	<% if (YCSX.getLoai().equals("1")){%>
								<TR class="tbheader">									
									<TH width="6%" align = "center" >Mã</TH>
									<TH width="22%" align = "center" >Tên sản phẩm</TH>
									<TH align = "center" width="8%">Tồn hiện tại </TH>
									<TH align = "center" width="8%">Tồn an toàn </TH>
									<TH align = "center" width="8%"><%= "Dự báo T." + t1 + "." + n1 %></TH>
									<TH align = "center" width="10%"><%= "Yêu cầu SX T." + t1 + "." + n1 %></TH>
							
									<TH align = "center" width="8%"><%= "Dự báo T." + t2 + "." + n2 %></TH>
									<TH align = "center" width="10%"><%= "Yêu cầu SX T." + t2 + "." + n2 %></TH>
				           			
									<TH align = "center" width="8%"><%= "Dự báo T." + t3 + "." + n3 %></TH>
									<TH align = "center" width="10%"><%= "Yêu cầu SX T." + t3 + "." + n3 %></TH>
									
								</TR>
								
					<%	
						if(data != null){
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							int i = 0;
							while(data.next()){ 
								if(i % 2 == 0){ %>
								<TR class = <%= lightrow %> >
					<%			}else{ %>	
								<TR class = <%= darkrow %> >		
					<% 			}%>			
									<TD align = "left"><%= data.getString("MA") %></TD>
									<TD><%= data.getString("TEN") %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("TONHIENTAI")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("TONANTOAN")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_THANG_1")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_THANG_1")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_THANG_2")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_THANG_2")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_THANG_3")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_THANG_3")) %></TD>
									
								</TR>
					<%			i++;
							}
						
						}%>	
						
	<%}else{ %>
								<TR class="tbheader">									
									<TH width="6%" align = "center" >Mã</TH>
									<TH width="20%" align = "center" >Tên sản phẩm</TH>
									<TH align = "center" width="7%">Tồn hiện tại </TH>
									<TH align = "center" width="7%">Tồn an toàn </TH>
									<TH align = "center" width="6%">Dự báo tuần 1</TH>
									<TH align = "center" width="7%">Yêu cầu SX tuần 1</TH>
							
									<TH align = "center" width="6%">Dự báo tuần 2</TH>
									<TH align = "center" width="7%">Yêu cầu SX tuần 2</TH>
				           			
									<TH align = "center" width="6%">Dự báo tuần 3</TH>
									<TH align = "center" width="7%">Yêu cầu SX tuần 3</TH>
									
									<TH align = "center" width="6%">Dự báo tuần 4</TH>
									<TH align = "center" width="7%">Yêu cầu SX tuần 4</TH>
									
									
								</TR>
								
					<%	
						if(data != null){
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							int i = 0;
							while(data.next()){ 
								if(i % 2 == 0){ %>
								<TR class = <%= lightrow %> >
					<%			}else{ %>	
								<TR class = <%= darkrow %> >		
					<% 			}%>			
									<TD align = "left"><%= data.getString("MA") %></TD>
									<TD><%= data.getString("TEN") %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("TONHIENTAI")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("TONANTOAN")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_TUAN_1")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_TUAN_1")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_TUAN_2")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_TUAN_2")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_TUAN_3")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_TUAN_3")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("DUBAO_TUAN_4")) %></TD>
									<TD align = "right"><%= formatter.format(data.getDouble("YCSX_TUAN_4")) %></TD>
									
								</TR>
					<%			i++;
							}
						
						}%>		
	
	
	<%} %>							
					</TABLE>
							
					</fieldset>
				</td>
				
			</tr>

		</table>				
</td></tr>
</table>
</form>
</body>
<script type="text/javascript">
</script>
</html>
<%
try
{
	if(data != null) data.close();
	if(clRs  != null) clRs.close();
	YCSX.DBClose();
	session.setAttribute("yc", null) ; 
}catch (Exception ex)
{
	ex.printStackTrace();
}
}%>