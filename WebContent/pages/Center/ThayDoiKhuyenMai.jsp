<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMaiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("ThayDoiKhuyenMaiSvl","");
	if(util==null||!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IThayDoiKhuyenMaiList obj = (IThayDoiKhuyenMaiList)session.getAttribute("obj"); %>
<% ResultSet nhaphanglist = (ResultSet)obj.getNhaphangList(); 
obj.setNextSplittings();


int[] quyen = new  int[6];
quyen = util.Getquyen("ThayDoiKhuyenMaiSvl","",userId);%>

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

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

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css"> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
});
</script>

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
    document.nhForm.sohoadon.value = "";
    document.nhForm.tungay.value = "";
    document.nhForm.denngay.value = "";
    document.nhForm.nppId.selectedIndex = 0;
    document.nhForm.trangthai.selectedIndex = 0;
    document.forms['nhForm'].submit();
}

function submitform()
{   
   document.forms["nhForm"].submit();
}
function seach()
{   
   document.forms["nhForm"].submit();
}
function xuatExcel()
{
	document.forms['nhForm'].action.value= 'toExcel';
	document.forms['nhForm'].submit();
	document.forms['nhForm'].action.value= '';
}

function  newform()
{
	document.forms["nhForm"].action.value ="new";
	document.forms["nhForm"].submit();
}
function processing(id,chuoi)
{
	 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
	 document.getElementById(id).href=chuoi;
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../ThayDoiKhuyenMaiSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value="1"> 
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting"value="<%= obj.getNxtApprSplitting() %>">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> 
        <TABLE width="100%" cellspacing="0" cellpadding="0">
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0">
                    	<TR>
                        	<TD align="left" class="tbnavigation">
                            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr height="22">
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %></TD>
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>  &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị &nbsp;</LEGEND>
                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                
								<TR>
									<TD class="plainlabel" >Từ ngày </TD>
						  			<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
											</TD>
										</TR>
										</TABLE>
									</TD>
								</TR>
									
								<TR>
                                    <TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange="submitform();">
												</TD>

                	                     	</TR>
										</TABLE>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  		<option value=""></option>
									<%if (obj.getTrangthai().equals("0")){ %>								  		
								    	<option value="0" selected>Chưa duyệt </option>
								    	<option value="1">Đã duyệt</option>
								    	<option value="2" >Đã hủy</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>								  		
								    	<option value="0" selected>Chưa duyệt </option>
								    	<option value="1" selected>Đã duyệt</option>
								    	<option value="2" >Đã hủy</option>
									<%  }else if (obj.getTrangthai().equals("2")){ %>								  		
								    	<option value="0" selected>Chưa duyệt </option>
								    	<option value="1">Đã duyệt</option>
								    	<option value="2" selected >Đã hủy</option>
									<% }else{  %>
										<option value="0" >Chưa duyệt </option>
								    	<option value="1">Đã duyệt</option>
								    	<option value="2" >Đã hủy</option>
									<% }}%>
								    	</select></TD>
								</TR>

                                <TR>
                                	<TD align="left" colspan="4">
                                	
                                	 
									
                                		 <a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>
										
										<a class="button2" href="javascript:submitform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
		<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;											
											
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
                              
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Thay đổi CTKM</LEGEND> 
                                <a
											class="button3" href="javascript:newform()"> <img
											style="top: -4px;" src="../images/New.png" alt="">Tạo
											mới </a>
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >ID </TH>                                            
                                            <TH >Mã trả KM </TH>
                                            <TH> Mã điều kiện KM</TH>
                                            <TH >Người tạo </TH>
                                            <TH >Ngày tạo </TH>
                                            <TH >Người sửa </TH>
                                            <TH >Ngày sửa </TH>
                                            <TH >Người duyệt </TH>
                                            <TH >Ngày duyệt </TH>
											<TH >Trạng thái </TH>
                                            <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
         
                               <% 
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(nhaphanglist != null){
								try{								
                                    while (nhaphanglist.next())
                                    {
                                    	String trangthai="";
                	                    if(nhaphanglist.getString("trangthai").equals("0"))
                	                    		trangthai="Chưa duyệt";
                	                    else if(nhaphanglist.getString("trangthai").equals("1"))
                	                    		{
                	                    			trangthai="Đã duyệt";
                	                    		}	
                                    	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        	<%}%>
                                                <TD align="left"><div align="left"><%= nhaphanglist.getString("thaydoiId") %></div></TD>
                                                <TD align="center"><%= nhaphanglist.getString("TraKm") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("dkKm") %></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NguoiTao") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NgayTao") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NguoiSua") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NgaySua") %></div></TD>
                                                <TD align="center"><%= nhaphanglist.getString("NguoiDuyet") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("NgayDuyet") %></TD>
                                                 <TD align="center"> <%= trangthai %></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR>
                                	                    <%
                                	                    
                                	                    
                                	                    if(nhaphanglist.getString("trangthai").equals("0")){ %>
                                	                    
                                	                     <%if(quyen[Utility.XEM]!=0){ %>
	                                	                    <TD>	                                    	                    
	                                	                    	<A href = "../../ThayDoiKhuyenMaiUpdateSvl?userId=<%=userId%>&display=<%=nhaphanglist.getString("thaydoiId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>															                                    	                   
	                                        	            </TD>
	                                        	            <%} %>
	                                        	            
                                	                    <%if(quyen[Utility.SUA]!=0){ %>
                                	                    	<TD>	                                    	                    
	                                	                    	<A href = "../../ThayDoiKhuyenMaiUpdateSvl?userId=<%=userId%>&edit=<%=nhaphanglist.getString("thaydoiId") %>"><img src="../images/Edit.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>															                                    	                   
	                                        	            </TD>
	                                        	          <%} %>
	                                        	          
	                                        	          <%if(quyen[Utility.CHOT]!=0){ %>
                                	                    	<TD>	                                    	                    
																	<A id='chotphieu<%=nhaphanglist.getString("thaydoiId")%>' href=""><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0" onclick="if(!confirm('Bạn có muốn thêm sp vào ctkm này hay không?')) {return false ;}else{ processing('<%="chotphieu"+nhaphanglist.getString("thaydoiId")%>' , '../../ThayDoiKhuyenMaiSvl?userId=<%=userId%>&chot=<%=nhaphanglist.getString("thaydoiId") %>');}"  ></A>															                                    	                   
	                                        	            </TD>
																  <%} %>			
	                                        	          
	                                        	          <%if(quyen[Utility.XOA]!=0){ %>
                                	                    	<TD>	                                    	                    
																	<A id='deletephieu<%=nhaphanglist.getString("thaydoiId")%>' href=""><img src="../images/Delete.png" alt="Xóa" width="20" height="20" title="Xóa" border="0" onclick="if(!confirm('Bạn có muốn xóa thêm sp vào ctkm này hay không?')) {return false ;}else{ processing('<%="deletephieu"+nhaphanglist.getString("thaydoiId")%>' , '../../ThayDoiKhuyenMaiSvl?userId=<%=userId%>&delete=<%=nhaphanglist.getString("thaydoiId") %>');}"  ></A>															                                    	                   
	                                        	            </TD>
	                                        	          <%} %>
	                                        	            
	                                        	           
                                        	            <%}else { %>
                                        	            
                                        	            <%if(quyen[Utility.XEM]!=0){ %>
	                                	                    <TD>	                                    	                    
	                                	                    	<A href = "../../ThayDoiKhuyenMaiUpdateSvl?userId=<%=userId%>&display=<%=nhaphanglist.getString("thaydoiId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>															                                    	                   
	                                        	            </TD>
	                                        	            <%} %>
                                        	            
                                        	            <%} %>
														</TR>
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
								}catch(java.sql.SQLException e){e.printStackTrace();}
                               		}%>
                               			<tr class="tbfooter" > 
                               			
                               			<TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
													<a href="javascript:View('nhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nhForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
                               			
                               			
                               			 </tr> 
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
</HTML>
<% if(!(nhaphanglist == null)) nhaphanglist.close(); %>
<% if(obj != null){
	obj.DBclose();
	obj = null;
	session.setAttribute("obj",null);
} %>
<% }%>