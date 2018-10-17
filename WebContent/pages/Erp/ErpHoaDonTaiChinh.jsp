<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.SQLException"%>

<% IErpHoaDonTaiChinh obj = (IErpHoaDonTaiChinh)session.getAttribute("obj"); %>
<% 	ResultSet hoadonlist = (ResultSet)obj.getListHoaDon();
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");

int[] quyen = new  int[5];
Utility util=new Utility();
quyen = util.Getquyen("ErpHoaDonTaiChinhSvl","&KM=0&NOIBO=0",userId);
NumberFormat formater = new DecimalFormat("##,###,###");
%>
<% obj.setNextSplittings(); %>

<%
	ResultSet npplist = (ResultSet) obj.getkhRs();
	ResultSet khoRs = obj.getKhoRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TraphacoERP - Project</title>
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
   
   	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
	    document.forms["erpHdForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpHdForm"].action.value = "new";
	    document.forms["erpHdForm"].submit();
	 }
	 function clearform()
	 {   
	    
	    document.forms["erpHdForm"].tungay.value = "";
	    document.forms["erpHdForm"].denngay.value = "";	    
	    document.forms["erpHdForm"].sohoadon.value = "";
	    document.forms["erpHdForm"].trangthai.value = "";	    
	    document.forms["erpHdForm"].nppId.value = "";	    
	    document.forms["erpHdForm"].sochungtu.value = "";
	    document.forms["erpHdForm"].masku.value = "";
	    document.forms["erpHdForm"].nguoitao.value = "";
	    document.forms["erpHdForm"].diachikh.value = "";
	    document.forms["erpHdForm"].khoId.value = "";
	    document.forms["erpHdForm"].pxkId.value = "";
	    submitform();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpHdForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpHdForm"].msg.value);
	 }
	 
	 function duyetform(Id)
	 {
	 	 if(!confirm('Bạn có chắc muốn chốt hóa đơn tài chính này?')) 
	 	 {
	 		 return false ;
	 	 }
	 	 
	 	 document.forms['erpHdForm'].chungtu.value = Id;
	 	 document.forms['erpHdForm'].action.value= 'chot';
	 	 document.forms['erpHdForm'].submit();
	 }
	 
	 function xoaform(Id)
	 {
	 	 if(!confirm('Bạn có chắc muốn xóa hóa đơn tài chính này?')) 
	 	 {
	 		 return false ;
	 	 }
	 	 
	 	 document.forms['erpHdForm'].chungtu.value = Id;
	 	 document.forms['erpHdForm'].action.value= 'delete';
	 	 document.forms['erpHdForm'].submit();
	 }
	 
	 function processing(id,chuoi)
	 {
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
		 document.getElementById(id).href=chuoi;
	 }
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
	
</head>
<body>
<form name="erpHdForm" method="post" action="../../ErpHoaDonTaiChinhSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="chungtu" id="chungtu" >
<input type="hidden" name="msg" value='<%= obj.getMessage() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Quản lý bán hàng > Xuất hóa đơn tài chính
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
                        	<TD class="plainlabel" width="150px">Từ ngày </TD>
                        	<TD class="plainlabel" width="300px">
                            	<input type="text" class="days" id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        	</TD>
                        
                        	<TD class="plainlabel" width="150px" >Đến ngày </TD>
                        	<TD class="plainlabel" >
                            	<input type="text" class="days" id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" onchange="submitform()" />
                        	</TD>
                         
                    	</TR>                  
                    
                    	<TR>                     
	                        <TD class="plainlabel" valign="middle">Số chứng từ </TD>
	                        <TD class="plainlabel" valign="middle">
	                            <input type="text" name="sochungtu" value="<%= obj.getId() %>" onchange="submitform()">
	                        </TD>   
	                        <TD class="plainlabel" valign="middle">Số hóa đơn </TD>
	                       		<TD class="plainlabel" valign="middle">
	                           		<input type="text" name="sohoadon" value="<%= obj.getSoHoaDon() %>" onchange="submitform()">
	                     		</TD> 
													
						</TR>
                    
                    	<TR>                   	 		 
                     		<TD class="plainlabel">Khách hàng</TD>
							<TD class="plainlabel">
								<select name="nppId" id="nppId" onChange="submitform();" style ="width:200px">
									<option value=""> </option>
									<%
										if(npplist != null)
										{
											try
											{
												while(npplist.next())
												{  									 
												if( npplist.getString("pk_seq").equals(obj.getNppId())){ %>
												<option value="<%= npplist.getString("pk_seq") %>" selected="selected" ><%= npplist.getString("pk_seq") %> - <%= npplist.getString("ten") %></option>
												<%		}else { %>
												<option value="<%= npplist.getString("pk_seq") %>" ><%= npplist.getString("pk_seq") %> - <%= npplist.getString("ten") %> </option>
												<% 		} 
												} 
											npplist.close();
											}catch(java.sql.SQLException ex){}
										}
								   %>
								</select>
							</TD>	
	                          <TD class="plainlabel" valign="middle">Trạng thái </TD>
	                        <TD class="plainlabel" valign="middle" colspan="3">
	                           <select name="trangthai" onChange="submitform();" style ="width:200px">
	                           		<option value=""> </option>
									<% if (obj.getTrangthai().equals("1")){%>
									  	<option value="1" selected>Đã chốt</option>
									  	<option value="0">Chưa chốt</option>
									  	<option value="2">Đã xóa</option>
									  <!-- 	<option value="4" >Đã xuất kho(chưa hoàn tất)</option>
									  	<option value="5" >Hoàn tất xuất kho </option> -->
									  
									<%}else if(obj.getTrangthai().equals("0")) {%>
									 	<option value="0" selected>Chưa chốt</option>
									  	<option value="1" >Đã chốt</option>
									 	<option value="2" >Đã xóa</option>
									  	
									<%}else if(obj.getTrangthai().equals("2")){%>											
									 	<option value="2" selected>Đã xóa</option>										  	
									  	<option value="0" >Chưa chốt</option>
									  	<option value="1" >Đã chốt</option>
									  	  	
									<%}else{%>
										<option value="0" >Chưa chốt</option>
									  	<option value="1" >Đã chốt</option>											
									  	<option value="2" >Đã xóa</option>
									  	<!-- <option value="4" >Đã xuất kho(chưa hoàn tất)</option>
									  	<option value="5" >Hoàn tất xuất kho</option> -->
									<%} %>
	                           </select>
	                        </TD>  
                    	</TR>
   					
   						<TR>
   							<TD class="plainlabel" valign="middle">Phiếu xuất kho</TD>
	                        <TD class="plainlabel" valign="middle">
	                            <input type="text" name="pxkId" id="pxkId" value="<%= obj.getpxkId() %>" onchange="submitform()">
	                        </TD> 
	                        <TD class="plainlabel" valign="middle">Mã/tên sản phẩm </TD>
	                        <TD class="plainlabel" valign="middle">
	                            <input type="text" name="masku" value="<%= obj.GetSKU() %>" onchange="submitform()">
	                        </TD> 
	   						                               
   						</TR>
   						<TR>
   							<TD class="plainlabel" valign="middle">Người tạo</TD>
	                        <TD class="plainlabel" valign="middle">
	                            <input type="text" name="nguoitao" value="<%= obj.getNguoitao() %>" onchange="submitform()">
	                        </TD> 
	                        <TD class="plainlabel" valign="middle">Địa chỉ khách hàng </TD>
	                        <TD class="plainlabel" valign="middle">
	                           <input type="text" name="diachikh" value="<%= obj.getDiachiKH()%>" onchange="submitform()">
	                        </TD> 
	   						                               
   						</TR>
   						<TR class="plainlabel" >
	                    	<TD class="plainlabel" >Kho </TD>
	                    	<TD class="plainlabel"  width="250px" colspan="4">
	                    		<select name = "khoId"   id = "khoId" class="select2" style="width: 200px" onchange="submitform();"> 
	                    		<option value=""> </option>
		                        	<%
		                        		if(khoRs != null)
		                        		{
		                        			try
		                        			{
		                        			while(khoRs.next())
		                        			{  
		                        			if( khoRs.getString("pk_seq").equals(obj.getKhoId())){ %>
		                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("ten") %></option>
		                        		 <% } } khoRs.close();} catch(SQLException ex){}
		                        		}
		                        	%>
	                    		</select>
	                   	 	</TD>			                   	 	
              			</TR>	
	                    <TR>
	                        <td colspan="4" class="plainlabel">
	                            <a class="button" href="javascript:submitform()">
	                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            <a class="button2" href="javascript:clearform()">
	                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
	                        </td>
	                    </TR>        					
                	</TABLE>                      
        		</fieldset>                      
    		</div>
    		
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Xuất HĐTC </span>&nbsp;&nbsp;
                	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                    <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 6%">Số chứng từ</TH>
	                    <TH align="center" style="width: 8%">Ngày xuất HD</TH>
	                    <TH align="center" style="width: 6%">Số hóa đơn</TH>
	                    <!-- <TH align="center" style="width: 6%"  >Số hóa đơn đính kèm</TH> -->
	                    <TH align="center" style="width: 8%">Tổng giá trị</TH>
	                    <TH align="center" style="width: 15%">Khách hàng</TH>
	                    <TH align="center" style="width: 6%">Trạng thái</TH>
	                    <TH align="center" style="width: 6%">Ngày tạo</TH>
	                    <TH align="center" style="width: 8%"> Người tạo </TH>
	                    <TH align="center" style="width: 6%"> Ngày sửa </TH>
	                    <TH align="center" style="width: 8%"> Người sửa </TH>
	                    <TH width="10%" align="center" >Tác vụ</TH>
	                </TR>
					<%
                 			int m = 0;
							for(int i =0; i < htList.size(); i ++)
							{  	
								IThongTinHienThi htt = htList.get(i);
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
			                    <TD align="center">
				                    <% if(htt.getTrangthai().equals("3")){ %>
				                    	<span style="color: red;" ><%= htt.getId() %></span> 
				                    <%} else { %>
				                    	<span><%= htt.getId() %></span> 
				                    <%} %>
		                    	</TD>
			                    <TD align="center"><%= htt.getNgaychungtu() %></TD>
			                    <TD align="center"><%= htt.getSohoadon() %></TD>
			                   <%--  <TD align="center"><%= htt.getSohoadon2() %></TD> --%>
			                    <TD align="center"><%= formater.format(Double.parseDouble(htt.getTongtien())) %></TD>
			                    <TD align="left"><%= htt.getTenKH() %></TD>	
			                    <TD align="center">
			                    	<%
			                    		String trangthai = "";
			                    		String tt = htt.getTrangthai();
			                    		if(tt.equals("0"))
			                    			trangthai = "Chưa chốt";
			                    		else
			                    		{
			                    			if(tt.equals("1"))
			                    				trangthai = "Đã chốt";
			                    			else
			                    			{
			                    				if(tt.equals("2"))
			                    					trangthai = "Đã xóa";
			                    				else if(tt.equals("4"))
				                    				trangthai = "Đã xuất kho(chưa hoàn tất)";
			                    				else if(tt.equals("5"))
				                    				trangthai = "Hoàn tất xuất kho ";
			                    				else trangthai="Đã hủy";
			                    			}
			                    		}
			                    	%>
			                    	<%= trangthai %>
			                    </TD>	                                    
			                    <TD align="center"><%= htt.getNgaytao() %></TD>
			                    <TD align="center"><%= htt.getNguoitao() %></TD>
			                    <TD align="center"><%= htt.getNgaysua() %></TD>	
			                    <TD width="8%" align="center"><%= htt.getNguoisua() %></TD>				
			                    <TD align="center"> 
			                    <%if(tt.equals("0")){ %>
				                    <%if(quyen[2]!=0){ %>
		                                <A href = "../../ErpHoaDonTaiChinhUpDateSvl?userId=<%=userId%>&update=<%= htt.getId() %>">
		                                	<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0 >
		                                </A>&nbsp;
		                                
		                            <%} %>
	                                <%if(quyen[3]!=0){ %>
	                                	 <A href="javascript:duyetform(<%= htt.getId() %>);" >
										 	<img  src="../images/Chot.png" alt="Chốt" width="20" height="20"  border='0' title="Chốt"	 >
										 </A>	
	                                <%} %>
	                                <%if(quyen[1]!=0){ %>
	                                 <A href="javascript:xoaform(<%= htt.getId() %>);" >
										 	<img  src="../images/Delete20.png" alt="Xóa" width="20" height="20"  border='0' title="Xóa"	 >
									 </A>
										 
	                               <%--  <A id='xoahd<%= htt.getId()%>' href = ""> 
	                                	<img src="../images/Delete20.png" alt="Xóa hóa đơn" title="Xóa Hóa Đơn" width="20" height="20" border=0 onclick="if(!confirm('Bạn muốn xóa hóa đơn này?')) {return false ;}else{ processing('<%="xoahd"+htt.getId()%>' , '../../ErpHoaDonTaiChinhSvl?userId=<%=userId%>&delete=<%=htt.getId()%>');}">                               
	                                </A> --%>	
	                                <%} %>		
	                               
			                    	<%}else{ %>
			                    	<%-- <%if(quyen[3]!=0){ 
			                    		if(htt.gethtxuathoadon().equals("0")){  %>
			                    		<%if( tt.equals("1")){  %>
	                                		<A id='xuathd<%=htt.getId() %>' href = "">
	                                			<img src="../images/xuatkho20.png" alt="Xuất kho" title="Xuất kho" width="20" height="20"  border=0 onclick="if(!confirm('Bạn muốn xuất kho cho hóa đơn này?')) {return false ;}else{ processing('<%="xuathd"+htt.getId()%>' , '../../ErpHoaDonTaiChinhSvl?userId=<%=userId%>&xuatkho=<%=htt.getId() %>');}">
	                                		</A>&nbsp;
	                                	<%} %>
	                                	<%if(tt.equals("4")){ %>
	                                		<img src="../images/choxuatkho.png" alt="Chờ Xuất kho" title="Chờ Xuất kho" width="20" height="20" border=0 >
		                                &nbsp;
		                                	
		                                <%} %>
		                                <%if(tt.equals("5")){ %>
		                                <img src="../images/daxuatkho.png" alt="Đã Xuất kho" title="Đã Xuất kho" width="20" height="20" border=0 >
		                                &nbsp;
		                                
		                          		  <%}} %>			                    
	                                <%} %> --%>
	                                
			                     	<A href = "../../ErpHoaDonTaiChinhUpDateSvl?userId=<%=userId%>&display=<%= htt.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
			                     				                     		                     	
			                    <%} %>
	                                <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
	                                
	                                	<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
		                    				<table width="90%" align="center">
								                 <tr>
						                           	<th width="50px">Nợ/Có</th>
						                            <th width="100px">Số hiệu tài khoản</th>
						                            <th width="150px">Số tiền</th>
						                            <th width="300px">Đối tượng</th>
						                            <th width="80px">Trung tâm CP</th>	
						                            <th width="80px">Trung tâm DT</th>										                       
								                 </tr>
		                        
					                            <% List<IDinhKhoanKeToan> ktList = htt.getLayDinhkhoanKT();							                       	 	
						                        		for(int sd = 0; sd < ktList.size(); sd++)
						                        		{
						                        			IDinhKhoanKeToan kt = ktList.get(sd);
							                        		%>
							                        			<tr>
							                        				<td>
							                        					<input type="text" style="width: 100%; text-align: left;" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
							                        				</td>
										                            <td>											                            	
										                            	<input type="text" style="width: 100%; text-align: left;" readonly="readonly" name="sohieutk" value=<%= kt.getSoHieu() %> />
										                            </td>
										                            <td>
										                            	<% if(kt.getSotien().trim().length()>0) {%>
										                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= formater.format(Double.parseDouble(kt.getSotien())) %>" />
										                            	<%} else {%>
										                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" /></td>
										                            	<%} %>
										                            <td>
										                            	<input type="text"  style="width: 100%; text-align: left;" name="doituong" value="<%= kt.getDoiTuong() %>" />
										                            </td>
										                            <td>
										                            	<input type="text"  style="width: 100%; text-align: left;" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
										                            </td>
										                            <td>
										                            	<input type="text"  style="width: 100%; text-align: left;" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
										                            </td>
										                        </tr>
							                        <%  } %>
				
		                    				</table>
									        
									        <div align="right">
									            <label style="color: red" ></label>
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                <a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
									        </div>
		                				</DIV>						
			                    </TD>
		                	</TR>
                     	<% m++; } %>
                     
						<tr class="tbfooter" >
							<TD align="center" valign="middle" colspan="14" class="tbfooter">
							<%System.out.println("Trang:"+obj.getNxtApprSplitting()+"bhgh:"+obj.getNextSplittings()); %>
							 	<%if(obj.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpHdForm', 1, 'view')"> &nbsp;
								<%}else {%>
									<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
									<%} %>
								<% if(obj.getNxtApprSplitting() > 1){ %>
									<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpHdForm', 'prev')"> &nbsp;
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
									<a href="javascript:View('erpHdForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
								<%} %>
									<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
								<%} %>
								
								<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpHdForm', 'next')"> &nbsp;
								<%}else{ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
								<%} %>
								<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
							   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
						   		<%}else{ %>
						   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpHdForm', -1, 'view')"> &nbsp;
						   		<%} %>
							</TD>
					 </tr>  
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

<script type="text/javascript">
	
	 <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 400, "click");
	   
	  <%}%>
	  
</script>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>