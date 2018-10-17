<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 	ILapngansach lnsBean = (ILapngansach)session.getAttribute("lnsBean"); %>
<% 	ResultSet tsList = (ResultSet) lnsBean.getTslist(); %>

<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<% 	ResultSet nsList = (ResultSet) lnsBean.getNsList(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">

#diengiai {

    width: 300px;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
#tabc tr input:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});
	


	var flag=false;
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	 function saveform()
	 { 
		 document.forms['lnsForm'].action.value='save';
	     document.forms['lnsForm'].submit();
	 }


	 function submitform()
	 { 
	     document.forms['lnsForm'].submit();
	 }
	 
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	 }

	 function Dinhdang(element)
	 {
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }
	 

</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="toBottom()">
<form name="lnsForm" method="post" action="../../LapngansachCDvaKHTSUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="ctyId" value='<%=lnsBean.getCtyId()  %>'>
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý ngân sách > Khấu hao và phân bổ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
		<%if(lnsBean.getView().equals("1")){ %>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
        <%} %>
    </div>
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= lnsBean.getMsg() %></textarea>
		         <% lnsBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Khấu hao và phân bổ</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            
               <TR>
	               <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	               <TD class="plainlabel" colspan = 3  >
	                   <SELECT name = "nsId"  style="width: 300px;" onChange = "submitform();">
	                       	<OPTION value = "" >&nbsp;</OPTION>
		                        <%
		                    if(nsList != null){
								while(nsList.next()){
									if(nsList.getString("nsId").equals(lnsBean.getLnsId())){
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
	             <TR>
                        <TD class="plainlabel" valign="middle" >Loại</TD>
                        <TD class="plainlabel" valign="middle" >
                            <SELECT id="loai"  name="loai" onChange="submitform();">

					<% 
						if(lnsBean.getLoai().equals("1")){ %>
                           		<OPTION value="1" selected>Tài sản</option>
                           		<OPTION value="2" >Công cụ dụng cụ</option>
                    <%	}else{ %>
                       		<OPTION value="1">Tài sản</option>
                    		<OPTION value="2" selected>Công cụ dụng cụ</option>
                    <%	} %>								                            	
                            </SELECT>
                        </TD>                          
                        
                 </TR>         


            </TABLE>
            </div>          
     </fieldset>
    </div>
    <div align="left" style="width:80%; float:none; clear:left">
	 <fieldset>
		<legend class="legendtitle">Khấu hao và phân bổ &nbsp;&nbsp;&nbsp;&nbsp;
		</legend>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD align="left" colspan="4">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

							<TR class="tbheader">
								<TH width="15%" align="center">Bộ phân</th>
								<TH width="25%" align="center">Hạng mục</th>
								<TH width="20%" align="center">Tổng giá trị</th>
								<TH width="10%" align="center">Số tháng khấu hao</th>
								
							<% if(lnsBean.getLoai().equals("1")){ %>
							
								<TH width="20%" align="center">Nhóm tài sản</th>
								
							<%}else{ %>
							
								<TH width="20%" align="center">Nhóm công cụ dụng cụ</th>
								
							<%} %>
							
								<TH width="10%" align="center">Phân bổ</th>
							</TR>

			<% 	
				int m = 0;
				if(tsList != null){ 
					try{			

						while(tsList.next()){
             					if((m % 2 ) == 0) {%>
							<TR class='tbdarkrow'>
								<%}else{ %>							
							<TR class='tblightrow'>
								<%} %>
													
								<TD align="left">
									<%= tsList.getString("BP") %>
									<INPUT TYPE = "hidden" NAME = "tsIds" VALUE = "<%=tsList.getString("TSID") %>" > 
								</TD>
								
								<TD align="left">
									<%= tsList.getString("DIENGIAI") %>								 
								</TD>

														
								<TD align="center">
								<%= formatter.format(Double.parseDouble(tsList.getString("THANHTIEN"))) %>
								</TD>
								
								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="sothangKH" + tsList.getString("TSID") %>' ID = "sothangKH" VALUE = "<%= Float.parseFloat(tsList.getString("SOTHANGKH")) %>" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" >
								</TD>

								<TD align="center">
									<SELECT name = "<%= "ntsId" + tsList.getString("TSID") %>" onChange ="saveform();" >
													<OPTION VALUE = "0">&nbsp</OPTION>
									<% 	ResultSet ntsList = (ResultSet) lnsBean.getNtslist(); %>
									<% if(ntsList != null){ 
											while(ntsList.next()){ 
												if(tsList.getString("NTSID").equals(ntsList.getString("NTSID"))){ %>
									
													<OPTION VALUE = "<%= ntsList.getString("NTSID") %>" selected><%= ntsList.getString("DIENGIAI") %></OPTION>
									
									<%			}else{ %>								
													<OPTION VALUE = "<%= ntsList.getString("NTSID") %>"  ><%= ntsList.getString("DIENGIAI") %></OPTION>
									
											
									<%			} 
											}
											ntsList.close();
										}
									%>
									</SELECT>
								</TD>				
								
								<TD>
				        			<a href="" id='<%="cpbId" + tsList.getString("TSID")  %>' rel= '<%="cpb" + tsList.getString("TSID") %>'>
       	 							&nbsp; <img alt="Chi tiết" src="../images/vitriluu.png"></a>
       	 		
                  					<DIV id='<%= "cpb" + tsList.getString("TSID") %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
		                            	background-color: white; width: 770px; height:250px; overflow:auto; padding: 2px;">
		                    			<TABLE width="100%" align="center" cellpadding="4">
		                        			<TR class="tbheader">
		                            			<TH width="300px" align="center">Mã</TH>
		                            			<TH width="400px" align="center">Diễn giải</TH>
		                            			<TH width="70px" align="center">Phần trăm</TH>				                            								
									        </TR>	
                            									<%
                            									
                            				ResultSet rs = lnsBean.Chonphanbo(tsList.getString("TSID"), tsList.getString("NTSID"));
                            									
                            				if(rs != null)
	                        				{
	                        					try{
	                        						int i = 0;
	                        						while(rs.next())
	                        						{  
	                        			
                        	                           	if (i % 2 != 0) {%>                     
	                                                   		<TR class='tblightrow'>
	                                             <%		} else {%>
	                                                        <TR class='tbdarkrow'>
	                                             <%		}%>

	            						           				<TD align="left"><%= rs.getString("MA") %></td>			            						            				
	            						           				<TD align="left"><%= rs.getString("DIENGIAI")%></TD>
	  		                        						    <TD align="center">
	  		                        						    <INPUT TYPE = "TEXT" NAME = <%="pt_" + tsList.getString("TSID") + "_" + rs.getString("TTCPID") %> VALUE = "<%= rs.getString("PHANTRAM") %>" style="width: 50px;">
	  		                        						    </TD>  
	                        					
	                        			                      </TR>
	                        			
	                        		 			<% 		i++;
	                        		 				} 
	                        						rs.close(); 
	                        												
	                        					}catch(java.sql.SQLException e){}
	                        				}%>

	                        									
		                    				</TABLE>
		                    					
		                     				<div align="left" class="legendtitle">
		                     					<label  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		                     								
		                     						<a href="javascript:dropdowncontent.hidediv('<%= "cpb" + tsList.getString("TSID") %>')" ></a>
		                     				</div>
		                     				<script type="text/javascript">
													dropdowncontent.init('<%="cpbId" + tsList.getString("TSID") %>', "left-bottom", 300, "click");
											</script>
		            				</DIV>                        
								
								
								</TD>
								
							</TR>	
				<%			m++;
						}
																	
					}catch(java.sql.SQLException e){}
				}%>				
				
						</TABLE>
					</TD>
				</TR>
			</TABLE>
     	
    </div>
</div>

</form>

</BODY>

</html>
<% 	
	if(tsList != null) tsList.close(); 
if(nsList != null) nsList.close(); 
	lnsBean.closeDB(); 
	
	session.setAttribute("lnsBean", null) ;  ; 
	
	%>
