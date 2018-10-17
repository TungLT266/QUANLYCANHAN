<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
NumberFormat formatter = new DecimalFormat("#,###,###");
IErpHoadonchuyenkhoNPPList obj = (IErpHoadonchuyenkhoNPPList)session.getAttribute("obj");
List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet khRs = obj.getKhRs();
	ResultSet HTbanhang = obj.getHtbhRs();
	ResultSet kenhbanhang = obj.getKbhRs();
	ResultSet khuvuc = obj.getKvRs();
		%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
   NumberFormat formater = new DecimalFormat("##,###,###");%>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpHoadonchuyenkhoNPPSvl","",userId);
		ResultSet rstien=obj.getRstien();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Phanam - Project</title>
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
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"	type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
  <!--  <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
   	
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
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    document.forms["ckParkForm"].khTen.value = "";
	    document.forms["ckParkForm"].sohoadon.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	</SCRIPT>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
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
			font-size: 13;
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>
	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpHoadonchuyenkhoNPPSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getnppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="phanloai" value='<%= obj.getPhanloai() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">	
        	&nbsp;Quản lý tồn kho > Nghiệp vụ kho > Hóa đơn khác 
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
                        <TD class="plainlabel" width="130px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" style="border-radius:4px; height: 20px;" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel" colspan="4">
                            <input type="text" style="border-radius:4px; height: 20px;" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     
                     	<TD class="plainlabel" >
                     		Mã / Tên Đối tượng
                     	</TD>
                        <TD class="plainlabel">
	                    	<input type="text" style="border-radius:4px; height: 20px;" name = "khTen" value="<%= obj.getKhTen() %>" onchange="submitform();" >
                        </TD>
                     	
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="4">
                           <select name="trangthai" onchange="submitform();" class="select2" style = "width:200px">
								<% if (obj.getTrangthai().equals("1")){%>
									<option value="" >ALL</option>
								  	<option value="1" selected>Chờ xác nhận</option>
								  	<option value="2">Đã xác nhận</option>
								  	<option value="3" >Đã xóa</option>
								  	<option value="4" >Đã in HĐ</option>
								  	<option value="5" >Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="" >ALL</option>
								 	<option value="1" >Chờ xác nhận</option>
								  	<option value="2" selected>Đã xác nhận</option>
								  	<option value="3" >Đã xóa</option>
								  	<option value="4" >Đã in HĐ</option>
								  	<option value="5" >Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("3")) {%>
								 	<option value="" >ALL</option>
								 	<option value="1" >Chờ xác nhận</option>
								  	<option value="2" >Đã xác nhận</option>
								  	<option value="3" selected>Đã xóa</option> 
								  	<option value="4" >Đã in HĐ</option> 
								  	<option value="5" >Đã hủy</option>	
								<%}else if(obj.getTrangthai().equals("4")) {%>
									<option value="" >ALL</option>
								 	<option value="1" >Chờ xác nhận</option>
								  	<option value="2" >Đã xác nhận</option>
								  	<option value="3" >Đã xóa </option> 
								  	<option value="4" selected>Đã in HĐ</option> 
								  	<option value="5" >Đã hủy</option>
								<% }else if(obj.getTrangthai().equals("5")) {%>
									<option value="" >ALL</option>
								 	<option value="1" >Chờ xác nhận</option>
								  	<option value="2" >Đã xác nhận</option>
								  	<option value="3" >Đã xóa </option> 
								  	<option value="4" >Đã in HĐ</option> 
								  	<option value="5" selected>Đã hủy</option>
							  	<% }else {%>
									<option value="" selected>ALL</option>
								 	<option value="1" >Chờ xác nhận</option>
								  	<option value="2">Đã xác nhận</option>
								  	<option value="3"  >Đã xóa </option>
								  	<option value="4" >Đã in HĐ</option>
								  	<option value="5" >Đã hủy</option>
							<%} %>
                           </select>
                        </TD>  
                        
                                          
                    </TR>
                 <TR>
							<TD  class="plainlabel">Số hóa đơn</TD>
							<TD  class="plainlabel" colspan="3" ><INPUT name="sohoadon" style="border-radius:4px; height: 20px;" type="text" size="30" value = '<%= obj.getSohoadon()%>' onChange = "submitform();"></TD>
							
									
					</TR>   
						                    
                    <TR>
                    	<TD class="plainlabel" width="100px">Tên SP</TD>
						<TD class="plainlabel">
							<input type="text" style="border-radius:4px; height: 20px;" name="tensp" value="<%= obj.getTensp() %>" onchange="submitform();" /></TD>
						<TD class="plainlabel" width="100px">Tên xuất HĐ</TD>
						<TD class="plainlabel">
							<input type="text"style="border-radius:4px; height: 20px;"  name="tenxuathd" value="<%= obj.getTenxuatHD() %>"  onchange="submitform();" /></TD>
                    </TR>
                     
                                        
                    <tr>
                        <td colspan="6" class="plainlabel">
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
            	<legend><span class="legendtitle"> Xuất hóa đơn </span>&nbsp;&nbsp;
            	
            	  <%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
                 <%} %>   
                 
                </legend>
            	<TABLE id="table" class="tabledetail sortable" width="100%" border="0" cellspacing="1" cellpadding="4">
            	<thead>
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%" >Mã số</TH>
	                    <TH align="center" style="width: 8%" >Ngày hóa đơn</TH>
	                    <TH align="center" style="width: 8%" >Số hóa đơn</TH>
	                    <TH align="center" style="width: 5%" >Kênh</TH>
	                    <TH align="center" style="width: 13%" >Khách hàng</TH>
	                    <TH align="center" style="width: 8%" >Trạng thái</TH>
	                    <TH align="center" style="width: 8%" >Tổng tiền</TH>
	                    <TH align="center" style="width: 8%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 8%" >Người tạo</TH>
	                    <TH align="center" style="width: 8%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 8%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
	                </thead>
	                <tbody>
					<%
                 			int m = 0;
							for(int i =0; i < htList.size(); i ++)
							{  		
								IThongTinHienThi ht = htList.get(i);
								String trangthai = "";
	                    		String tt = ht.getTrangthai();
	                    		if(tt.equals("1")){ //NPP TAO
	                    			trangthai = "Chờ xác nhận";
	                    		}else
	                    		{
	                    			if(tt.equals("2")) {
		                    			trangthai = "Đã xác nhận";
	                    			}else{
	                    				if(tt.equals("4")) 
	                    					trangthai = "Đã in HĐ";
	                    				else
	                    				{
	                    					if(tt.equals("3")) 
		                    					trangthai = "Đã xóa";
	                    					else 
	                    						trangthai = "Đã hủy";
	                    				}
	                    			}
	                    			
	                    		}
								String msg=ht.getTooltip();
								
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow' >
		                        <%}else{ %>
		                          	<TR class='tblightrow' >
		                        <%} %>
		                    <TD align="center" ><%= ht.getId() %></TD>
		                    <TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getNgaychungtu() %></TD>
		                    <TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getSohoadon() %></TD>
		                     <TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getKenhBanHang() %></TD>
		                    <TD ><%= ht.getTenKH() %></TD>  
		                    <TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %>>
		                    	<%= trangthai %>
		                    </TD>   
		                    <TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %>><%= formater.format(Double.parseDouble(ht.getTongtien())) %></TD>									                                    
					     	<TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getNgaytao() %></TD>	
		                    <TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getNguoitao() %></TD>
         					<TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getNgaysua() %></TD>
							<TD align="center" <%if(ht.getTrangthai().equals("3")||ht.getTrangthai().equals("5")) {%> style="color: red" <%} %> ><%= ht.getNguoisua() %></TD>
									
		                    <TD align="center"> 
		                    <%
		                     if(tt.equals("1")){ %>
								<%if(quyen[Utility.SUA]!=0){ %>
									  <A href = "../../ErpHoadonchuyenkhoNPPUpdateSvl?userId=<%=userId%>&update=<%= ht.getId() %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                
                                <%if(quyen[Utility.CHOT]!=0){ %>
                                	<A href = "../../ErpHoadonchuyenkhoNPPSvl?userId=<%=userId%>&chot=<%= ht.getId() %>"><img src="../images/Chot.png" alt="Duyệt hóa đơn" title="Duyệt hóa đơn" width="20" height="20" border=0 ></A>&nbsp;
                              	<%} %>
                              	
                              	<%if(quyen[Utility.XOA]!=0){ %>
                              		<A href = "../../ErpHoadonchuyenkhoNPPSvl?userId=<%=userId%>&delete=<%= ht.getId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Delete20.png" alt="Xóa hóa đơn" title="Xóa hóa đơn" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa hóa đơn này?')) return false;"></A>									
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
					                            <% List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
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
		                				                		
		                    <%} else{ %>
		                    	
		                    	<%if(quyen[Utility.XEM]!=0){ %>
		                    		<A href = "../../ErpHoadonchuyenkhoNPPUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                   		<%} %>
		                   		
		                   		<%  if( !tt.equals("3") && !tt.equals("5") ) { %>	
		                   			<A href = "../../ErpHoadonchuyenkhoNPPPdfSvl?userId=<%=userId%>&pdf=<%=ht.getId()%>">
    								<img src="../images/Printer30.png" alt="Print"  title="Print" width="20" height="20" border ="0" longdesc="Print" style="border-style:outset"></A>
		                   			
		                   			<%if(quyen[Utility.XOA]!=0){ %>
	                              		<A href = "../../ErpHoadonchuyenkhoNPPSvl?userId=<%=userId%>&delete=<%= ht.getId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Delete20.png" alt="Xóa hóa đơn" title="Xóa hóa đơn" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa hóa đơn này?')) return false;"></A>									
			                  		<%} %>	
		                   			
		                    	<% } %>
		                    		
		                    <% 
		                       }%>
		                    </TD>
		                </TR>
                     <% m++; }  %>
                     </tbody>
                     <tfoot>
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
					  </tfoot>
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

</body>
</html>

<% 
	if( nhapkhoRs != null)
	{
		nhapkhoRs.close();
		nhapkhoRs = null;
	}
	if( khRs != null)
	{
		khRs.close();
		khRs = null;
	}
	
	htList.clear();
	htList = null;
	
	obj.DBclose();
	session.setAttribute("obj", null);
	
%>

<%}%>