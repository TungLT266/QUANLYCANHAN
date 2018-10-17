<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.hoadontaichinh.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% ISoathoadon obj = (ISoathoadon)session.getAttribute("obj"); %>
<% ResultSet hdRs =  obj.getHoadonRs(); %>
<% ResultSet khRs = obj.getKhRs(); %>
<% ResultSet nvbhRs = obj.getNvbhRs(); 
   NumberFormat formater = new DecimalFormat("#,###,###");%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Diageo/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("DoihoadonTTSvl", "22",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

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
   
    <SCRIPT language="javascript" type="text/javascript">
		 function submitform()
		 {   
		    document.forms["ckParkForm"].submit();
		 }
		 
		 function saveform()
		 {	
			 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		 	 document.forms['ckParkForm'].action.value = 'saveDOIHOADON';
		     document.forms['ckParkForm'].submit();
		 }
		 
		 function xuatExcel()
		 {
		 	document.forms['ckParkForm'].action.value= 'excel';
		 	document.forms['ckParkForm'].submit();
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
					{//Phím Delete và Phím Back
						return;
					}
					return false;
				}
			}
		
	
	</SCRIPT>
	
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
<form name="ckParkForm" method="post" action="../../SoathoadonTTSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="loaihoadon" value="<%= obj.getLoaidonhang() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Bán hàng OTC > Đổi số hóa đơn 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    
    <div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	
        <span id="btnSave">
	        <A href="javascript:saveform();" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        
        <A href="javascript:xuatExcel();" >
	        	<IMG src="../images/excel.gif" title="Xuất file excel" alt="Xuất file excel" border ="1px" style="border-style:outset; width: 30px;"></A>
        
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= obj.getMsg() %></textarea>
		         <% obj.setMsg(""); %>
    	</fieldset>
  	</div>
    
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="130px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" >Số hóa đơn từ</TD>
                        <TD class="plainlabel" >
                            <input type="text" name="sohoadontu" value="<%= obj.getSohoadontu() %>" onkeypress="return keypress(event);" />
                        </TD>
                    
                        <TD class="plainlabel" >Số hóa đơn đến</TD>
                        <TD class="plainlabel">
                            <input type="text" name="sohoadonden" value="<%= obj.getSohoadonden() %>" onkeypress="return keypress(event);" />
                        </TD>
                    </TR> 
                    
                    <TR>
                        <TD class="plainlabel" >VAT</TD>
                        <TD class="plainlabel" >
                            <select name="ptVAT" >
                            	<option value="" selected="selected" ></option>
                            	<% if(obj.getPtVat().equals("5")) { %>
                            		<option value="5" selected="selected" >5 %</option>
                            	<% } else { %>
                            		<option value="5" >5 %</option>
                            	<% } %>
                            	<% if(obj.getPtVat().equals("10")) { %>
                            		<option value="10" selected="selected" >10 %</option>
                            	<% } else { %>
                            		<option value="10" >10 %</option>
                            	<% } %>

                            </select>
                        </TD>
                    
                        <TD class="plainlabel" >Trạng thái</TD>
                        <TD class="plainlabel">
                            <select name="trangthai" >
                            	<option value="" selected="selected" ></option>
                            	<% if(obj.getTrangthai().equals("1")) { %>
                            		<option value="1" selected="selected" >Chờ xác nhận</option>
                            	<% } else { %>
                            		<option value="1" >Chờ xác nhận</option>
                            	<% } %>
                            	<% if(obj.getTrangthai().equals("2")) { %>
                            		<option value="2" selected="selected" >Đã xác nhận</option>
                            	<% } else { %>
                            		<option value="2" >Đã xác nhận</option>
                            	<% } %>
                            	<% if(obj.getTrangthai().equals("3")) { %>
                            		<option value="3" selected="selected" >Đã xóa</option>
                            	<% } else { %>
                            		<option value="3" >Đã xóa</option>
                            	<% } %>
                            	<% if(obj.getTrangthai().equals("5")) { %>
                            		<option value="5" selected="selected" >Đã hủy</option>
                            	<% } else { %>
                            		<option value="5" >Đã hủy</option>
                            	<% } %>
                            	<% if(obj.getTrangthai().equals("4")) { %>
                            		<option value="4" selected="selected" >Đã in HĐ</option>
                            	<% } else { %>
                            		<option value="4" >Đã in HĐ</option>
                            	<% } %>
                            	
                            </select>
                        </TD>
                       
                    </TR> 
                    
                    <TR  >
                     
                     	<TD class="plainlabel" >Nhân viên bán hàng</TD>
                        <TD class="plainlabel"  >
                            <select name = "nvbhId" class="select2" style="width: 200px"  >
	                    		<option value=""> </option>
	                        	<%
	                        		if(nvbhRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nvbhRs.next())
	                        			{  
	                        			if( nvbhRs.getString("pk_seq").equals(obj.getNvbhId())){ %>
	                        				<option value="<%= nvbhRs.getString("pk_seq") %>" selected="selected" ><%= nvbhRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nvbhRs.getString("pk_seq") %>" ><%= nvbhRs.getString("ten") %></option>
	                        		 <% } } nvbhRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                     	
                     	<TD class="plainlabel" width="100px">Khách hàng</TD>
                        <TD class="plainlabel" colspan="3" >
                            <select name = "khId" class="select2" style="width: 400px" >
	                    		<option value=""> </option>
	                        	<%
	                        		if(khRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(khRs.next())
	                        			{  
	                        			if( khRs.getString("pk_seq").equals(obj.getKhTen())){ %>
	                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
	                        		 <% } } khRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                     	            
                    </TR> 
                    
                    <TR>
                    	<TD class="plainlabel" >Tổng tiền trước thuế</TD>
                        <TD class="plainlabel" >
                            <input type="text" name="tttruocthue" value="<%= formater.format(obj.getTongtientruocthue()) %>" onkeypress="return keypress(event);" />
                        </TD>
                    
                        <TD class="plainlabel" >Tổng tiền thuế</TD>
                        <TD class="plainlabel">
                            <input type="text" name="ttthue" value="<%= formater.format(obj.getTongtienthue()) %>" onkeypress="return keypress(event);" />
                        </TD>
                    </TR>
                    
                     <TR>
                    	<TD class="plainlabel" >Số tiền hóa đơn</TD>
                        <TD class="plainlabel" >
                            <input type="text" name="tthoadon" value="<%=  formater.format(obj.getTongtienhoadon()) %>" onkeypress="return keypress(event);" />
                        </TD>
                       <TD class="plainlabel" >Tổng hóa đơn</TD>
                        <TD class="plainlabel" >
                            <input type="text" name="tongsohoadon" value="<%=  formater.format(obj.getTongSLHD()) %>" onkeypress="return keypress(event);" />
                        </TD>
                        
                    </TR>
                    <TR>
						<TD class="plainlabel" colspan="4">
							<a class="button2" href="javascript:submitform();"> 
								<img style="top: -4px;" src="../images/Search30.png" alt="">Lọc hóa đơn </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR>
			
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Soát hóa đơn tài chính</span>&nbsp;&nbsp;</legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%" >Mã </TH>
	                    <TH align="center" style="width: 7%" >Ngày </TH>
	                    <TH align="center" style="width: 9%" >Số hóa đơn</TH>
	                    <TH align="center" style="width: 9%" >Số hóa đơn mới</TH>
	                    <TH align="center" style="width: 1% ; display: none" >Ký hiệu <br /> hóa đơn</TH>
	                    <TH align="center" style="width: 1% ; display: none" >Loại <br > hóa đơn</TH>
	                    <TH align="center" style="width: 1% ; display: none" >Trạng thái</TH> 
	                    <TH align="center" style="width: 13%" >Mã <br > khách hàng</TH>
	                    <TH align="center" style="width: 23%" >Họ tên</TH>
	                    <TH align="center" style="width: 11%; color: red;" >Tổng tiền <br > trước thuế</TH>
	                    <TH align="center" style="width: 11%; color: red;" >Tổng tiền <br > thuế</TH>
	                    <TH align="center" style="width: 11%; color: red;" >Số tiền <br > hóa đơn</TH>
	                </TR>
					
					<% if(hdRs != null) {
						
						NumberFormat format = new DecimalFormat("#,###,###");
						int count = 0;
						
						while(hdRs.next())
						{
							String trangthai = "";
                    		String tt = hdRs.getString("trangthai");
                    		if(tt.equals("1")){ //NPP TAO
                    			trangthai = "Chờ xác nhận";
                    		}
                    		else
                    		{
                    			if(tt.equals("2")) {
	                    			trangthai = "Đã xác nhận";
                    			}else{
                    				if(tt.equals("3")) 
                    					trangthai = "Đã xóa";
                    				else 
                    				{
                    					if(tt.equals("5")) 
	                    					trangthai = "Đã hủy";
                    					else
                    						trangthai = "Đã in HĐ";
                    				}
                    			}
                    			
                    		}
                    		
							%>
							
							<TR>
								<Td><input type="text" name="mahoadon" style="width: 100%" readonly="readonly" value="<%= hdRs.getString("pk_seq") %>"  > </Td>						
								<Td><input type="text" name="ngayhoadon" style="width: 100% ;"  value="<%= hdRs.getString("ngayxuatHD").trim() %>"  readonly="readonly" > </Td>
								<Td>
									<input type="text" name="sohoadon" style="width: 100%"  value="<%= hdRs.getString("sohoadon").trim() %>" onkeypress="CheckTab_Enter('<%= count %>');" readonly="readonly" > 
									<input type="hidden" name="type" value="<%= hdRs.getString("type") %>"  > 
								</Td>
								<Td>
									<input type="text" name="sohoadonMOI" style="width: 100%"  value="" onkeypress="return keypress(event); "> 
									
								</Td>
								
								<Td style=" display: none"><input type="text" name="kyhieu" style="width: 100% ;"  value="<%= hdRs.getString("kyhieu").trim() %>" onkeypress="CheckTab_Enter('<%= count %>');" > </Td>
								<Td style=" display: none"><input type="text" name="loai" style="width: 100% ;" readonly="readonly" value="<%= hdRs.getString("loaiHD") %>" > </Td>
								<Td style=" display: none"><input type="text" name="trangthai" style="width: 100% ; " readonly="readonly" value="<%= trangthai %>" > </Td>
								
								 
								<Td><input type="text" name="makhachhang" style="width: 100%" readonly="readonly" value="<%= hdRs.getString("MAFAST") %>" disabled="disabled" > </Td>
								<Td><input type="text" name="hoten" style="width: 100%" readonly="readonly" value="<%= hdRs.getString("khTEN") %>" disabled="disabled" > </Td>
								
								<Td><input type="text" name="sotienBVAT" style="width: 100%; text-align: right; color: red;" readonly="readonly" value="<%= format.format(hdRs.getDouble("tongtienBVAT")) %>" > </Td>
								<Td><input type="text" name="sotien" style="width: 100%; text-align: right; color: red;" readonly="readonly" value="<%= format.format(hdRs.getDouble("VAT")) %>"  > </Td>
								<Td><input type="text" name="sotienAVAT" style="width: 100%; text-align: right; color: red;" readonly="readonly" value="<%= format.format(hdRs.getDouble("tongtienAVAT")) %>"  > </Td>
								
							</TR>
							
						<% count++; }
						hdRs.close();
					} %>
					 
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>
</html><%}%>