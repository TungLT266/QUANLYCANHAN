<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.giamgiahangban.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.giamgiahangban.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpGiamgiahangban obj =(IErpGiamgiahangban)session.getAttribute("csxBean");
	ResultSet poRs = (ResultSet)obj.getPORs();
	String[] hdSanpham = obj.getHoadonTen();
	String[] hdid = obj.getHoadonId();
	
	String[] ghichuhd = obj.getGhichu();
	String[] ngayhd = obj.getNgayHoaDon();
	String[] kyhieuhd = obj.getKyhieu();
	String[] thanhtien = obj.getTongtien();
	String[] sotientanggiam = obj.getSotien();
	ResultSet tienteList = obj.getTienteRs();
	NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
	NumberFormat formatter2 = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>

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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<SCRIPT language="JavaScript" type="text/javascript">

	function replaces()
	{
		var nccId = document.getElementById("nccId");
		var nccTen = document.getElementById("nccTen");
		
		var tem = nccId.value;
		if(tem == "")
		{
			nccTen.value = "";
		}
		else
		{
			if(parseInt(tem.indexOf(" - ")) > 0)
			{
				nccId.value = tem.substring(0, parseInt(tem.indexOf(" - ")));
				
				nccTen.value = tem.substr(parseInt(tem.indexOf(" - ")) + 3);
				
				if(nccId.value != '')
				{
					document.forms['khtt'].action.value='submit';
				    document.forms["khtt"].submit();
				}
			}
		}
		
		UpdateTotal();
		
		setTimeout(replaces, 300);
	}

	function UpdateTotal()
	{
		//var thanhtien = document.getElementsByName("thanhtien");
		var sotientanggiam = document.getElementsByName("sotientanggiam");
		
		var total = 0;
		for(var i = 0; i < sotientanggiam.length; i++)
		{
			if(sotientanggiam.item(i).value != '')
			{
				var tt = sotientanggiam.item(i).value.replace(/,/g,"") ;
				total = parseFloat(total) + parseFloat(tt);
			}
		}
		
		var vat = document.getElementById("vat").value.replace(/,/g,"");
		
		document.getElementById("bvat").value = DinhDangDonGia(total);
		document.getElementById("avat").value = DinhDangDonGia( parseFloat(total) + (  parseFloat(total) * vat / 100 ) );
		
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed==109)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function FormatTien(e)
	{
		if(e.value == '')
			e.value = '0';
		else
		{
			e.value = DinhDangDonGia(e.value);
		}
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100+0.50000000001);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
 
	
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpGiamgiahangbanUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=obj.getId() %>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán > Giảm/Tăng giá hàng bán > Hiển thị </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpGiamgiahangbanSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
							    <A href="../../ErpGiamgiahangbanPdfSvl?userId=<%=userId %>&print=<%=obj.getId() %>" ><img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:red ; font-weight:bold"  style="width: 100% ; color:#red ; background-color: white ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin Giảm/Tăng giá hàng bán</LEGEND>
						
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
	                          <TR  >
	                          		<TD class="plainlabel" valign="middle" width="140px" >Ngày ghi nhận</TD>   
			                        <TD class="plainlabel" valign="middle" width="280px" >
			                        	<input type="text" class="days" name="ngayghinhan" value="<%= obj.getNgayghinhan() %>"   > 
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="100px" >Ngày hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" class="days" name="ngayhoadon" value="<%= obj.getNgayhoadon() %>"   > 
			                        </TD>          
			                  </TR> 
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Ký hiệu hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text"  name="kyhieuhoadon" value="<%= obj.getKyhieuhoadon() %>"   > 
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="120px" >Số hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text"  name="sohoadon" value="<%= obj.getSohoadon() %>"   > 
			                        </TD>          
			                  </TR> 
	                          <TR>
	                          		<TD class="plainlabel" valign="middle" width="120px" >Diễn giải </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3" >
			                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
			                        </TD>          
			                  </TR> 
	                          <TR>
	                          		<TD class="plainlabel" valign="middle" >Khách hàng</TD>   
			                        <TD class="plainlabel" valign="middle" width="220px" >
			                        	<input type="text" id="nccId" name="nccId" value="<%= obj.getNccId() %>"  > 
			                        </TD>  
			                        <TD class="plainlabel" valign="middle" width="120px" >Tên </TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" name="nccTen" id="nccTen" value="<%= obj.getNccTen() %>" style="width: 400px;" > 
			                        </TD>           
			                  </TR> 
			                  
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Từ ngày</TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" onchange="submitform();"  > 
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="120px" >Đến ngày</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" onchange="submitform();"  > 
			                        </TD>          
			                  </TR> 
			                  <TR> 
                    				<TD class="plainlabel">Tiền tệ</TD>
                    				<TD class="plainlabel"  colspan = "3">
                    	
			                        	<SELECT name="tienteId" id="tienteId" style = "width:200px" onChange = "submitform();">
                        					<OPTION value = "">&nbsp;</OPTION>
                        	<%				
            	            			if(tienteList != null)
                	        			{
                    	    				try
                        					{
                        						while(tienteList.next())
                        						{  
                        							if( tienteList.getString("pk_seq").equals(obj.getTienteId())){ %>
                        					
                        						<OPTION value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></OPTION>
                        					
                        					<%		}else { %>
                        						
                        						<OPTION value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></OPTION>
                        		 		<% 			} 
                        						} 
                        						tienteList.close();} catch(SQLException ex){}
                        				}
                        	%>
                        				</SELECT>
	                          		</TD>
	                          </TR>
	                          
	                          <TR>
	                          		<TD class="plainlabel" valign="middle" width="120px" >Số hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3" >
			                        	
			                        	<a href="" id="hdId" rel="subcontentHd" >
			           	 							<img alt="Chọn hóa đơn" src="../images/vitriluu.png"></a>
			           	 				<DIV id="subcontentHd" style=" height:300px; overflow:scroll ; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                             background-color: white; width: 600px; padding: 4px;">
						                    <table width="90%" align="center">
						                        <tr>
						                            <th width="120px" style="font-size: 11px;"> Số hóa đơn</th>
						                            <th width="100px" style="font-size: 11px;"> Ký hiệu</th>
						                            <th width="150px" style="font-size: 11px;"> Ngày hóa đơn</th>
						                           
						                            <th width="200px" style="font-size: 11px;"> Tổng tiền HD</th>
						                             <th width="200px" style="font-size: 11px;">Số tiền tăng giảm</th>
						                              <th width="300px" style="font-size: 11px;"> Ghi chú</th>
						                              
						                             
						                          <!--   <th width="100px" style="font-size: 11px;"> Chọn</th> -->
						                        </tr>
						                        
						                        <%
					                        				  if(hdSanpham != null ) { 
					                        					  for(int i = 0; i < hdSanpham.length; i++ ) { %> 
					                        		  
					                        			<tr>
								                        	<td>
								                        	<input name="hoadonid" type="hidden" style="width: 100%;   text-align: left;" value="<%= hdid[i] %>" >
								                        		<input name="hdSanpham" type="text" style="width: 100%;   text-align: left;" value="<%= hdSanpham[i] %>" > 
								                        	</td>
								                        	<td>
								                        		<input name="kyhieuhd" type="text" style="width: 100%; text-align: left;" value="<%= kyhieuhd[i]  %>" > 
								                        	</td>
								                        	<td>
								                        		<input name="ngayhd" type="text" style="width: 100%; text-align: center;" readonly="readonly" value="<%= ngayhd[i] %>" >
								                        	</td>
								                        	
								                        	<td>
								                        		<input name="thanhtienhd" type="text" style="width: 100%; text-align: center;" readonly="readonly"  value="<%=thanhtien[i] %>" >
								                        	</td>
								                        		<td>
								                        		<input name="sotientanggiam"  onkeyup="FormatTien(this);" type="text" style="width: 100%; text-align: center;"   value="<%=sotientanggiam[i] %>" >
								                        	</td>
								                        	<td>
								                        		<input  name="ghichuhd"  type="text" style="width: 100%; text-align: center;"  value="<%=ghichuhd[i] %>" >
								                        	</td>
								                        	 
								                        </tr>
						                        			
					                        		 <%  
					                        					  }
					                        				  }
					                        	%>
						                        
						                       
						                        
						                    </table>
						                     <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontentHd')"   >Tiếp tục</a></div>
						                </DIV>
													                        
			                        </TD>          
			                  </TR> 
			                  
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Số tiền giảm / tăng trước VAT</TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text" id="bvat"  name="bvat"  style="text-align:right;" value="<%= obj.getBvat() %>" readonly="readonly"  > <%= obj.getDVTiente() %>
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="120px" >VAT</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        <% if(obj.getTienteId().equals("100000")){ %>
			                        	<input type="text"  id="vat" name="vat" style="text-align:right;" value="<%= obj.getVat() %>"   > %
			                        <%}else{ %>
			                        	<input type="text"  id="vat" name="vat" style="text-align:right;" value="0"  disabled > %
			                        <%} %>
			                        </TD>          
			                  </TR> 
			                  
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Số tiền giảm / tăng sau VAT</TD>   
			                        <TD class="plainlabel"  valign="middle" colspan="3" >
			                        	<input type="text" id="avat"  name="avat"  style="text-align:right;" value="<%= obj.getAvat() %>"  readonly="readonly" > <%= obj.getDVTiente() %>
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

<script type="text/javascript">
	replaces();
	
	dropdowncontent.init('hdId', "right-top", 300, "click");
	
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpKhachHangGiamGiaList.jsp");
	});	
</script>

</BODY>
</HTML>
<%
try{
	if(poRs != null) poRs.close();
	if(tienteList != null) tienteList.close();
}catch (Exception ex)
{
	ex.printStackTrace();
}finally
{
	if (obj != null)
	{
		obj.DbClose();
		obj = null;
	}
	session.removeAttribute("csxBean");
}	
}%>
