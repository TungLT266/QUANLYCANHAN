<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErpDenghingungsanxuat"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<% dbutils db = new dbutils(); %> 

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpDenghingungsanxuat obj =(IErpDenghingungsanxuat)session.getAttribute("obj");
	ResultSet huyRs = obj.getDenghingungsanxuatRs();
	ResultSet dvkd = obj.getDvkdRs();
%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>

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
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['dnhm'].nam.value= "";
	    document.forms['dnhm'].thang.value= "";
	    document.forms['dnhm'].dvkdId.value = "";
		document.forms['dnhm'].submit();
	}

	function submitform()
	{
		document.forms['dnhm'].action.value= 'search';
		document.forms['dnhm'].submit();
	}

	
	
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dnhm" method="post" action="../../ErpDenghingungsanxuatSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="lxsId" value="" >
<input type="hidden" name="kbsxId" value="" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Đề nghị ngưng sản xuất</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                                                      
                    		 <TR>
		                        <TD class="plainlabel" width="15%">Năm</TD>
		                        <TD class="plainlabel">
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
                        		<TD class="plainlabel" width="15%">Tháng</TD>
                        		<TD class="plainlabel">
		                        	<SELECT name="thang" style="width: 100px;" onChange = "submitform();">
										<option value = '0'>&nbsp;</option>
		                        <%	int thang = 0;
		                        
		                        	if(obj.getThang().length() > 0 ) 
		                        		thang = Integer.parseInt(obj.getThang());
		                        			                        		                        	
		                        	for(int m = 1; m <= 12; m++){
		                        		if(thang == m){ %>
		                        			<option value="<%= m %>" selected="selected"><%="Tháng " + m %></option>
		                       <% 		}else{ %>
		                					<option value="<%= m %>" ><%="Tháng " + m %></option>
		                       <% 		}
		                        	}
		                        %>
		                        
		                        	</SELECT>
                        		
                        		</TD>

							</TR> 
                              
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
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
                            <LEGEND class="legendtitle">&nbsp;Đề nghị ngưng sản xuất &nbsp;&nbsp;	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            		<TD width="98%">
                            			<TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
                                				<TR class="tbheader">
                                    				<TH width="7%" align="center">Mã</TH>
                                    				<TH width="25%" align="center">Tên sản phẩm</TH>
                                    				<TH width="7%" align="center">Năm</TH>
                                    				<TH width="5%" align="center">Tháng</TH>                                   				                                    				
                                    				<TH width="7%" align="center">Đề nghị sản xuất</TH>                                    
                                    				<TH width="7%" align="center">Lệnh sản xuất</TH>
                                    				<TH width="7%" align="center">Đề nghị ngưng</TH>                                    
                                    				<TH width="7%" align="center">Chi tiết</TH>
                                				</TR>
                                <% 
                                   
                                    int m = 0;
                                    int count = 0; 
                                    
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    String Id = "";
                                    String Id_old = "";
                                    ResultSet rs;
                                    if(huyRs != null){
                                    	while (huyRs.next()){
                                    		Id = huyRs.getString("Id");
                                    		if(!Id.equals("Id_old")){
                                        		if (m % 2 != 0) {%>                     
                                            		<TR class= <%=lightrow%> >
                                       			<%} else {%>
                                            		<TR class= <%= darkrow%> >
                                       			<%}%>
														<TD align="center"><%= huyRs.getString("SPID")%> </TD>
														<TD align="left"><%= huyRs.getString("SP")%></TD>
														<TD align="center"><%= huyRs.getString("NAM")%></TD>
														<TD align="center"><%= huyRs.getString("THANG")%></TD>
                                                		<TD align="right"><%= formatter.format(huyRs.getDouble("QTY"))%></TD>
                                                		<TD align="right"><%= formatter.format(huyRs.getDouble("SANXUAT")) %> </TD>
                                                		<TD align="right"><%= formatter.format(huyRs.getDouble("DENGHINGUNG")) %> </TD>
									  					<TD  align="center" >
						        			                 <a href="" id='<%="chitietId" + huyRs.getString("SPID") + huyRs.getString("NAM") + huyRs.getString("THANG") %>' rel= '<%="chitiet" + huyRs.getString("SPID") + huyRs.getString("NAM") + huyRs.getString("THANG") %>'>
	           	 														&nbsp; <img alt="Chi tiết" src="../images/vitriluu.png"></a>
	           	 		
                          										<DIV id='<%= "chitiet" + huyRs.getString("SPID") + huyRs.getString("NAM") + huyRs.getString("THANG") %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
				                             						background-color: white; width: 500px; height:250px; overflow:auto; padding: 2px;">
				                    								<TABLE width="100%" align="center">
				                        								<TR class="tbheader">
				                            								<TH width="100px" align="center">Mã lệnh sản xuất</TH>
				                            								<TH width="100px" align="center">Số lượng</TH>
				                            								<TH width="120px" align="center">Ngày dự kiến hoàn thành</TH>				                            								
												                        </TR>	
		                            									<%
		                            									
		                            									rs = obj.getChitiet(huyRs.getString("SPID"), huyRs.getString("NAM"), huyRs.getString("THANG"));
		                            									
		                            									if(rs != null)
			                        									{
			                        										try{
			                        											while(rs.next())
			                        											{  %>
			                        			
									                        			<TR>
			            						            				<TD align="center"><%= rs.getString("PROID") %></td>			            						            				
			            						            				<TD align="center"><%= formatter.format(rs.getDouble("SANXUAT"))%></TD>
			  		                        							    <TD align="center"><%= rs.getString("NGAYDUKIENHT")%></TD>  
			                        					
			                        			                        </TR>
			                        			
			                        		 								<% } 
			                        											rs.close(); 
			                        												
			                        										}catch(java.sql.SQLException e){}
			                        									}%>

			                        									
				                    								</TABLE>
				                    					
				                     							<div align="left" class="legendtitle">
				                     								<label  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				                     								
				                     								<a href="javascript:dropdowncontent.hidediv('<%= "chitiet" + huyRs.getString("SPID") + huyRs.getString("NAM") + huyRs.getString("THANG") %>')" ></a>
				                     							</div>
				                     							<script type="text/javascript">
																	dropdowncontent.init('<%="chitietId" + huyRs.getString("SPID") + huyRs.getString("NAM") + huyRs.getString("THANG") %>', "left-bottom", 300, "click");
																</script>
				            							</DIV>                        
																				
														</TD>
                                                		
                                            		</TR>
                                       	<% 
                                       			m++;
                                    		}
                                    		Id_old = Id;
                                        }
									}
                                    %>  
                                          			<TR class="tbfooter" >
														<TD align="center" colspan="15"> &nbsp; </TD>
													</TR>                                                  
                            			</TABLE>
                            		</TD>
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
		if (dvkd != null)
			dvkd.close(); 
		if (db != null)
			db.shutDown();
		if(huyRs != null)
			huyRs.close();

		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {
		e.printStackTrace();
	} 
	%>
<%}%>