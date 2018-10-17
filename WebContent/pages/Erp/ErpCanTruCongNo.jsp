<%@page import="geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNoList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNo"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>

<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.cantrucongno.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<%
	String[] trangthaiMa = new String[] { "0", "1", "2" };
%>
<% 
   IErpCanTruCongNoList obj =(IErpCanTruCongNoList) session.getAttribute("obj");

  List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<%
	String[] trangthaiTen = new String[] { "Chưa chốt", "Đã chốt","Đã hủy" };
%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% 
obj.setNextSplittings();  
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	

	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpCanTruCongNoSvl","",userId);


%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<style type="text/css">
.color
{
	background-color: red;
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="../scripts/Numberformat.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
    <SCRIPT language="javascript" type="text/javascript">
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
	 
	 function search()
	 {
		 document.forms["erpDmhForm"].action.value = "search";
		 submitform();
	 }
	 
	 function capnhat(PK_SEQ)
	 {
		 document.forms["erpDmhForm"].selectedItem.value = PK_SEQ;
		 document.forms["erpDmhForm"].action.value = "update";
		 submitform();
	 }
	 
	 
	 function xoa(PK_SEQ)
	 {
		 document.forms["erpDmhForm"].selectedItem.value = PK_SEQ;
		 document.forms["erpDmhForm"].action.value = "delete";
		 submitform();
	 }
	 
	 function chot(PK_SEQ)
	 {
		 document.forms["erpDmhForm"].selectedItem.value = PK_SEQ;
		 document.forms["erpDmhForm"].action.value = "chot";
		 submitform();
	 }
	 
	 
	 function newform()
	 {   
		document.forms["erpDmhForm"].action.value = "new";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].ncc.value = "";
	    document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].kh.value = "";
	    document.forms["erpDmhForm"].khId.value = "";
	    document.forms["erpDmhForm"].trangthai.value = "";
	    document.forms["erpDmhForm"].ctId.value = "";	
	    search();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
	
	<%if(obj.getMsg().trim().length()>0) {%>
	<script type="text/javascript">
	alert('<%=obj.getMsg()%>');	
	</script>
	<%obj.setMsg(""); %>
	<% }%>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpCanTruCongNoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" id="selectedItem"  name="selectedItem" value="#" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value=''>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải trả > Cấn trừ công nợ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn Admin &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    	
    	
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        
            	<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
		                   
                    		<TR>
                        		<TD class="plainlabel" valign="middle" width="15%" >Số phiếu</TD>
                        		<TD class="plainlabel" valign="middle" width="25%" >
		                     		<input type="text" id="ctId" name="ctId" value = "<%=obj.getId() %>"  onchange="search();"  >  
		                     		
                        		</TD>                        
                    		
                        		<TD class="plainlabel" valign="middle" width="15%" >Nhà cung cấp</TD>
                        		<TD class="plainlabel" valign="middle" >
		                     		<input type="text" id="ncc" name="ncc" value = "<%=obj.getNcc() %>"  >  
		                     		<input type="hidden" id="nccId" name="nccId" value = "" onkeypress="keypress2(event);" >
                        		</TD>                        
                    		</TR>
                    		<TR>
                        		<TD class="plainlabel" valign="middle" >Khách hàng</TD>
                        		<TD class="plainlabel" valign="middle">
		                     		<input type="text" id="kh" name="kh" value = "<%=obj.getKh() %>" >  
		                     		<input type="hidden" id="khId" name="khId" value = "" >
                        		</TD>  
                        		<TD  class="plainlabel">Trạng thái</TD>
												<TD class="plainlabel"><select name="trangthai"
													onChange="search();">
													<option value=""></option>
														<%if(obj!=null) {%>
														<%
															for (int i = 0; i < trangthaiMa.length; i++) {
														%>
														<%
															if (trangthaiMa[i].equals(obj.getTrangthai())) {
														%>
														<option value="<%=trangthaiMa[i]%>" selected><%=trangthaiTen[i]%></option>
														<%
															} else {
														%>
														<option value="<%=trangthaiMa[i]%>"><%=trangthaiTen[i]%></option>
														<%
															}
														%>
														<%
															}
														%>
														<%} %>

												</select></TD>                      
                    		</TR>
                    		
                             
                            
										
											<tr class="plainlabel"> 
                             	<td colspan="4" > 
                             		<a class="button" href="javascript:submitform()">
                               		 <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
               <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Cấn trừ công nợ &nbsp;&nbsp;	
                            	
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	      
                            </LEGEND>     
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
						<TH align="center">Số phiếu </TH>
	                    <TH align="center">Nhà cung cấp</TH>
	                    <TH align="center">Khách hàng</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Tổng tiền</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
	               <%   int m = 0;								
								for(int i =0; i < htList.size(); i ++)
								{
									IThongTinHienThi ht = htList.get(i);
									if((m % 2 ) == 0) { %>
		                         		<TR class="tbdarkrow">
			                     <%}else{ %>
			                          	<TR class="tblightrow">
			                        <%} %>
			                        <TD align="left"> <%=ht.getId()%>  </TD>
			                        <TD align="left"> <%=ht.getTenNCC()%> </TD>
			                        <TD align="left"> <%=ht.getTenKH()%> </TD>
			                        
			                        <% 
			                          String trangthai = ht.getTrangthai() ;
			                          String tt = "";
			                          if(trangthai.equals("0"))  tt =  "Chưa chốt";
			                          else if(trangthai.equals("1")) tt =  "Đã chốt"; 
			                          else tt =  "Đã hủy"; 
									%>
			                         <TD align="left"> <%=tt%> </TD>							
                                 
								
								<TD align="right" ><%= formatter.format(Double.parseDouble(ht.getTongtien())) %></TD>
								<TD align="center" ><%=ht.getNgaytao()%></TD>
								
								
									<TD align="center"><%-- <A href =<%= "../../ErpCanTruCongNoUpdateSvl?userId="+userId +"&display="+listCanTruCongNo.getString("PK_SEQ")+"" %> ><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;  --%>
									<%if(quyen[4]!=0){ %>
									
										<%} %>
										<%//if(quyen[4]!=0 &&listCanTruCongNo.getString("TRANGTHAI").trim().equals("0") ){ %>
										<%if(ht.getTrangthai().trim().equals("0") ){ %>
										<a href=<%="javascript:capnhat("
							+ ht.getId() + ")"%>> <img border="0" title="Cập nhật" alt="Cập nhật"
										src="../images/Edit20.png"></img> </a> 
										
										<a  href=<%="javascript:chot("
							+ ht.getId() + ")"%>> <img
										width="20" height="20" border="0"
										onclick="if(!confirm('Bạn có chắc chốt cấn trừ này?')) {return false ;}else{ processing('<%="chotphieu"+ ht.getId()%>' , '../../ErpCanTruCongNoSvlSvl?userId=<%= userId %>&chot=<%= ht.getId() %>');}"
										title="Chốt" alt="Chốt cấn trừ"
										src="../images/Chot.png"></img> </a>
										
										
										<%-- 
										
										<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; 
											     <img alt="Định khoản kế toán" src="../images/vitriluu.png">
										 </A> &nbsp;
											 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
							                            <th width="100px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="150px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>												                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
								                        		for(int sd = 0; sd < ktList.size(); sd++)
								                        		{
								                        			IDinhKhoanKeToan kt = ktList.get(sd);
									                        		%>
									                        			<tr>
									                        				<td>
									                        					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
									                        				</td>
												                            <td>											                            	
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
												                            </td>
												                            <td>
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  kt.getSotien() %>" style="text-align: left" /></td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
												                            </td>
												                        </tr>
									                        <%  }
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
								                     </div>
	                							</DIV> --%>
										
										<a href=<%="javascript:xoa("
							+ ht.getId() + ")"%>>

										<img width="20" height="20" border="0"
										onclick="if(!confirm('Bạn có muốn xóa cấn trừ này?')) return false;"
										title="Xóa cấn trừ" alt="Xóa"
										src="../images/Delete20.png"></img> </a>
										
										<%}else{ %>
										<a href=<%="javascript:capnhat("
							+ ht.getId() + ")"%>> <img border="0" title="Hiển thị" alt="Hiển thị"
										src="../images/Display20.png"></img> </a>
									
									  <%--  <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; 
											     <img alt="Định khoản kế toán" src="../images/vitriluu.png">
										 </A> &nbsp;
											 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
							                            <th width="100px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="150px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>												                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
								                        		for(int sd = 0; sd < ktList.size(); sd++)
								                        		{
								                        			IDinhKhoanKeToan kt = ktList.get(sd);
									                        		%>
									                        			<tr>
									                        				<td>
									                        					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
									                        				</td>
												                            <td>											                            	
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
												                            </td>
												                            <td>
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  kt.getSotien() %>" style="text-align: left" /></td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
												                            </td>
												                        </tr>
									                        <%  }
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
								                     </div>
	                							</DIV> --%>
									<%} %>
									</TD>

								</TR>				
	                
	               			 <% m++; } %>

							<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
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
								<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>  
				</TABLE>
            
            
        </div>
    </div>  
</div>
<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
</script>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">
jQuery(function()
		{		
	$("#ncc").autocomplete("ErpNhaCungCapList.jsp");
	$("#kh").autocomplete("ErpKhachhangCanTruList.jsp");
	
		});	
</script>
<%} %>
</body>

</html>
<%
try{
	if( htList!=null){
		htList.clear();
	}
	obj.DBClose(); 
	session.setAttribute("obj",null);
	session.setAttribute("backAttribute",obj);
}catch(Exception er){
	
}



%>