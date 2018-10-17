<%@page import="geso.traphaco.erp.beans.hoadon.IHdDetail"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon"%>
<%@page import="geso.traphaco.erp.beans.hoadon.IErpHoaDonList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpHoaDon obj = (IErpHoaDon)session.getAttribute("obj"); %>
<% List<IErpHoaDonList> hdList = (List<IErpHoaDonList>)obj.getHoadonList();   %>
<% List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList(); %>
<% ResultSet hoadonlist = (ResultSet)obj.getListHoaDon(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");

	int[] quyen = new  int[5];
	Utility util=new Utility();
	quyen[0]=1;
	quyen[1]=1;
	quyen[2]=1;
	quyen[3]=1;
	quyen[4]=1;
	quyen = util.Getquyen("ErpHoaDonTraveNccSvl","",userId);
	
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

%>
<% obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Best - Project</title>
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
   
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
   
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
		 document.forms["erpHdForm"].dontrahang.value = "";
	    document.forms["erpHdForm"].tungay.value = "";
	    document.forms["erpHdForm"].denngay.value = "";
	    document.forms["erpHdForm"].nppId.value = "";
	    document.forms["erpHdForm"].masku.value = "";
	    document.forms["erpHdForm"].sochungtu.value = "";
	    document.forms["erpHdForm"].sohoadon.value = "";
	    document.forms["erpHdForm"].ngayhoadon.value = "";
	    document.forms["erpHdForm"].trangthai.value = "";
	    
	    document.forms["erpHdForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpHdForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpHdForm"].msg.value);
	 }
	 
	 function processing(id,chuoi)
	 {
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
		 document.getElementById(id).href=chuoi;
	 }
	</SCRIPT>
</head>
<body>
<form name="erpHdForm" method="post" action="../../ErpHoaDonTraveNccSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMessage() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Nghiệp vụ khác > Xuất HD trả về NCC
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
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                   
                        <TD class="plainlabel" width="15%">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <TR  >
                        <TD class="plainlabel" valign="middle">Số chứng từ </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sochungtu" value="<%= obj.getId() %>" onchange="submitform()">
                        </TD>   
                        <TD class="plainlabel" valign="middle">Số hóa đơn </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sohoadon" value="<%= obj.getSoHoaDon() %>" onchange="submitform()">
                        </TD>                       
                    </TR>
                     <TR  >
                        <TD class="plainlabel" valign="middle">Khách hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="nppId" value="<%= obj.getNppId() %>" onchange="submitform()">
                        </TD> 
                        <TD class="plainlabel" >Ngày xuất hóa đơn </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="ngayhoadon" name="ngayhoadon" value="<%= obj.getNgayxuathd() %>" maxlength="10" onchange="submitform()" />
                        </TD>                       
                    </TR>
                    
                    
      
                    
                   
                    <TR style="display: none;" >
                        <TD class="plainlabel" valign="middle">Mã/tên sản phẩm </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="masku" value="<%= obj.GetSKU() %>" onchange="submitform()">
                        </TD>                        
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" >
                           <select name="trangthai" onChange="submitform();">
                           		
								<% if (obj.getTrangthai().equals("1")){%>
								    <option value=""> </option>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2">Đã xóa</option>
								  	
								  
								<%}else if(obj.getTrangthai().equals("0")) {%>
								    <option value=""> </option>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								 	<option value="2" >Đã xóa</option>
								  	
								  	
								<%}else if(obj.getTrangthai().equals("2")){%>	
								    <option value=""> </option>										
								 	<option value="2" selected>Đã xóa</option>										  	
								  	<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	
								  		  	
								<%}else{%>
									<option value="" selected> </option>
									<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>											
								  	<option value="2" >Đã xóa</option>
								<%} %>
                           </select>
                        </TD>  
                        
                      
                        <TD class="plainlabel" valign="middle">Số đơn trả hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="dontrahang" value="<%= obj.getSodontrahang() %>" onchange="submitform()">
                                             
                    </TR>                      
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Hóa đơn trả về NCC </span>&nbsp;&nbsp;
                	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                    <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số chứng từ</TH>
	                    <TH align="center">Ngày xuất HD</TH>
	                    <TH align="center">Số hóa đơn</TH>
	                    <TH align="center">Nhà cung cấp</TH>
	                    <TH align="center">Số tiền sau VAT</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH width="10%" align="center" >Tác vụ</TH>
	                </TR>
					<%
					 int count = 0;
	                    if(hdList!=null)
                    	for(int i =0; i < htList.size(); i ++)
						{ 
                    		IThongTinHienThi ht = htList.get(i);                       	

            					if((count % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center">
		                    <% if(ht.getTrangthai().equals("3")){ %>
		                    	<span style="color: red;" ><%= ht.getId() %></span> 
		                    <%} else { %>
		                    	<span><%= ht.getId() %></span> 
		                    <%} %>
		                    </TD>
		                    <TD align="center"><%= ht.getNgayxuat() %></TD>
		                    <TD align="center"><%= ht.getSohoadon() %></TD>	
		                    <TD align="left"><%= ht.getKhachhang()%></TD>	
		                    <TD align="left"><%=  formatter.format(Double.parseDouble(ht.getSOTIENVND())) %></TD>
		                    
		                    <%String trangthai = "";
                    		String tt = ht.getTrangthai();
                    		String htt=ht.getHoantat(); %>
                    		
		                    <TD align="center" <%if(!tt.equals("0") && !tt.equals("1") ) {%> style="color: red" <%} %>>
		                    	<%
		                    		
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				trangthai = "Đã chốt";
// 		                    	 
		                    			else
		                    			{
		                    				if(tt.equals("2"))
		                    					trangthai = "Đã xóa";
		                    				else trangthai="Đã hủy";
		                    			}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>	                                    
		                    <TD align="center"><%= ht.getNgaytao() %></TD>
		                    <TD align="center"><%= ht.getNguoitao() %></TD>
		                    <TD align="center"><%= ht.getNgaysua() %></TD>	
		                    <TD width="9%" align="center"><%= ht.getNguoisua() %></TD>				
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    <%if(quyen[2]!=0){ %>
                                <A href = "../../ErpHoaDonTraveNccUpDateSvl?userId=<%=userId%>&update=<%=  ht.getId() %>">
                                	<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0 >
                                </A>&nbsp;
                                <%} %>
                                <%if(quyen[3]!=0){ %>
                                <A id='chothd<%=ht.getId() %>' href = ""> <%-- "../../ErpHoaDonShopSvl?userId=<%=userId%>&chot=<%= hoadonlist.getString("pk_Seq") %>"> --%>
                                	<img src="../images/Chot.png" alt="Chôt nhập kho" title="Chốt Hóa Đơn" width="20" height="20" 
                               			 border=0 onclick="if(!confirm('Bạn muốn chốt hóa đơn này?')) {return false ;}else{ processing('<%="chothd"+ ht.getId()%>' , '../../ErpHoaDonTraveNccSvl?userId=<%=userId%>&chot=<%= ht.getId() %>');}">
                                </A>&nbsp;
                                <%} %>
                                <%if(quyen[1]!=0){ %>
                                <A id='xoahd<%=ht.getId()%>' href = "">  
                                	<img src="../images/Delete20.png" alt="Xóa nhập kho" title="Xóa Hóa Đơn" width="20" height="20" 
                                	border=0 onclick="if(!confirm('Bạn muốn xóa hóa đơn này?')) {return false ;}else{ processing('<%="xoahd"+ ht.getId() %>' , '../../ErpHoaDonTraveNccSvl?userId=<%=userId%>&delete=<%= ht.getId() %>');}">                               
                                </A>	
                                <%} %>	
                                
                                <A href="" id="ktlist<%=count %>" rel="subcontentKT<%=count%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
                                <DIV id="subcontentKT<%=count%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		
						                                 List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
						                                 if(ktList.size() > 0)
											                {
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
												                            	<%if(kt.getSotien().trim().length() > 0){ %>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                            	<%} else {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
												                            	<%} %>												                            	
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
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=count%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>            	                							
		                    <%} else { %>
		                    <%if(quyen[3]!=0){ %>
		                     	<A href = "../../ErpHoaDonTraveNccUpDateSvl?userId=<%=userId%>&HoanTat=<%=  ht.getId() %>">
                                	<IMG src="../images/convert.gif" alt="Chua Hoan Tat" title="Hoàn tất hóa đơn" 
                                	border=0 width="20px" height="20px">
                                </A>&nbsp;
                                <%} %>
		                     	<A href = "../../ErpHoaDonTraveNccUpDateSvl?userId=<%=userId%>&display=<%=  ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;		                     	
		                      <A href="" id="ktlist<%=count %>" rel="subcontentKT<%=count%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
                                <DIV id="subcontentKT<%=count%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		
						                                 List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
						                                 if(ktList.size() > 0)
											                {
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
												                            	<%if(kt.getSotien().trim().length() > 0){ %>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                            	<%} else {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
												                            	<%} %>												                            	
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
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=count%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>     
		                    <%} %>
		                    </TD>
		                </TR>
                     <% count++;  } %>
                     
					<tr class="tbfooter" >
						<TD align="center" valign="middle" colspan="14" class="tbfooter">
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
	  <%for(int k=0; k < count; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
</script>
</form>
</body>
</html>
<% 
if(obj!=null)
{
	obj.DBClose();	
}


%>