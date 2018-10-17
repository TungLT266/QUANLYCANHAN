<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.thutien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% IErpThutienList obj = (IErpThutienList)session.getAttribute("obj"); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>
<% ResultSet khList = (ResultSet)obj.getKhList(); %>
<% ResultSet nvList = (ResultSet)obj.getNvList(); %>
<% ResultSet htttList = (ResultSet)obj.getHtttList(); %>
<% ResultSet tthdList = (ResultSet)obj.getTThoadonList(); %>
<% ResultSet nguoisuaList = (ResultSet)obj.getNguoisuaRs(); %>
<% ResultSet kbhRs = (ResultSet)obj.getKbhRs(); %>
<% ResultSet tthdRs = (ResultSet)obj.getTThoadonList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
   ResultSet rskhoid=obj.getKhohangRs();
   ResultSet nvgnRs=obj.getNvgnRs();
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	
obj.setNextSplittings();   
int[] quyen = new  int[5];
quyen = util.Getquyen("ErpThutienSvl","",userId);

NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Traphaco - Project</title>
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
	 function newform()
	 {   
		document.forms["erpDmhForm"].action.value = "Tao moi";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].sochungtu.value = "";
	    document.forms["erpDmhForm"].khachhang.value = "";
	    document.forms["erpDmhForm"].trangthai.value = "";
	    document.forms["erpDmhForm"].nguoisua.value = "";
	    document.forms["erpDmhForm"].kbhId.value = "";
	    document.forms["erpDmhForm"].khohhid.value = "";
	    document.forms["erpDmhForm"].ghichu.value = "";  
	    document.forms["erpDmhForm"].sobangke.value = "";
	    document.forms["erpDmhForm"].submit();
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
	 
	 function xuatExcel() {
			document.forms['erpDmhForm'].action.value = 'excel';
			document.forms['erpDmhForm'].submit();
		}
	 
	 function TinhTien()
		{
			// từng sản phẩm
			
		    var sotien = document.getElementById("sotientt").value;
			if(sotien == ''){
				 document.getElementById("sotientt").value = '';
			}
			else{
				document.getElementById("sotientt").value = DinhDangTien(sotien);
			}
			
		}
	 
	 function formatTien(e)
		{
			var giatrinhap = e.value;
			e.value = DinhDangTien(giatrinhap);
			
		}
		
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function duyetform(Id, trangthai)
	{
		 if(!confirm('Bạn có muốn duyệt phiếu thu này?')) 
		 {
			 return false ;
		 }
		 
		 document.forms['erpDmhForm'].chungtu.value = Id;
		 document.forms['erpDmhForm'].trangthaiphieu.value = trangthai;
		 document.forms['erpDmhForm'].action.value= 'chot';
		 document.forms['erpDmhForm'].submit();
	}
	
	function xoaform(Id, trangthai)
	{
		 if(!confirm('Bạn có muốn hủy phiếu thu này?')) 
		 {
			 return false ;
		 }
		 
		 document.forms['erpDmhForm'].chungtu.value = Id;
		 document.forms['erpDmhForm'].trangthaiphieu.value = trangthai;
		 document.forms['erpDmhForm'].action.value= 'delete';
		 document.forms['erpDmhForm'].submit();
	}
	</SCRIPT>
</head>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

<body>
<form name="erpDmhForm" method="post" action="../../ErpThutienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="chungtu" id="chungtu"  >
<input type="hidden" name="trangthaiphieu" id="trangthaiphieu"  >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải thu > Thu tiền 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px "  >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        
                        <TD class="plainlabel"  >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" >Mã phiếu </TD>
                        <TD class="plainlabel" >
                            <input type="text" id="sochungtu" name="sochungtu" maxlength = "15" value="<%= obj.getsochungtu() %>"  onchange="submitform()" />
                        </TD>
                        
                    <TD class="plainlabel"  >Mã/Tên đối tượng</TD> 
	                    <TD class="plainlabel"   colspan="3">
	                       	<input  type="text"  name="khachhang" value="<%= obj.getKhId() %>" onchange="submitform()" >
	                     </TD> 
	                     
                    </TR>

					<TR>
						<TD class="plainlabel" >Trạng thái</TD>
						<TD class="plainlabel" >
							<select name = "trangthai" id= "trangthai" style = "width:200px" onchange="submitform()">								
								<%if(obj.getTrangthai().equals("0")){ %>
								<option value=""></option>
								<option value="0" selected="selected" >Chưa chốt</option>
								<option value="1" >Đã chốt</option>
								<option value="2" >Đã xóa</option>
								<%} else if(obj.getTrangthai().equals("1")){ %>
								<option value=""></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" selected="selected" >Đã chốt</option>
								<option value="2" >Đã xóa</option>
								<%} else if(obj.getTrangthai().equals("2")){ %>
								<option value=""></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" >Đã chốt</option>
								<option value="2" selected="selected" >Đã xóa</option>
								<%} else { %>
								<option value="" selected="selected" ></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" >Đã chốt</option>
								<option value="2" >Đã xóa</option>
								<%} %>
							</select>
						</TD>
						
						<TD class="plainlabel" valign="middle" >Người lập </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nguoisua" id="nguoisua" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(nguoisuaList != null)
	                        		{
	                        			while(nguoisuaList.next())
	                        			{  
	                        			if( nguoisuaList.getString("pk_seq").equals(obj.getNguoisuaId())){ %>
	                        				<option value="<%= nguoisuaList.getString("pk_seq") %>" selected="selected" ><%= nguoisuaList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoisuaList.getString("pk_seq") %>" ><%= nguoisuaList.getString("ten") %></option>
	                        		 <% } } nguoisuaList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>
					</TR>
					
                    <TR>
                         
                      <TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>
                      <TD class="plainlabel" valign="middle">
                          <select name="kbhId" id="kbhId" style = "width:200px" onchange="submitform()">
                          	<option value=""></option>
                          	<%
                       		if(kbhRs != null)
                       		{
                       			while(kbhRs.next())
                       			{  
                       			if( kbhRs.getString("pk_seq").equals(obj.getKbhId())){ %>
                       				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("diengiai") %></option>
                       			<%}else { %>
                       				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("diengiai") %></option>
                       		 <% } } kbhRs.close();
                       		}
                       	%>
                          </select>
                      </TD>    
                                          	 
                      <TD  class="plainlabel">Kho hàng hóa</TD>
							<TD  class="plainlabel" colspan="2" >
							<select name="khohhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">  </option>
								<%if(rskhoid!=null){ while (rskhoid.next()){ %>
								<option value="<%=rskhoid.getString("pk_seq")%>" <%if(rskhoid.getString("pk_seq").equals(obj.getKhohangId())){%> selected="selected" <%}%>><%=rskhoid.getString("ten") %>  </option>
								<%} }%>
								
							</select>
					 </TD> 
                       
                    </TR> 
                    
                     <TR>                                               
                        <TD class="plainlabel" >Ghi chú </TD>
                        <TD class="plainlabel" >
                            <input type="text" id="ghichu" name="ghichu" value="<%= obj.getGhichu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" >Số chứng từ </TD>
                        <TD class="plainlabel" colspan="1">
                            <input type="text" id="maChungTu" name="maChungTu" maxlength= "15" value="<%= obj.getMaChungTu() %>"  onchange="submitform()" />
                        </TD>
<!--                       
                        
                    </TR>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                           <!--  <a class="button3" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a> -->
                        </td>
                    </tr>  
                    <TR>                                               
                        <TD class="plainlabel" >Từ số tiền </TD>
                        <TD class="plainlabel" >
                            <input type="text" id="tusotien" name="tusotien" value="<%= obj.getTusotien().equals("")?"":formatterVND.format(Double.parseDouble(obj.getTusotien())) %>"  onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" >Đến số tiền </TD>
                        <TD class="plainlabel" colspan="1">
                            <input type="text" id="densotien" name="densotien" value="<%= obj.getDensotien().equals("")?"":formatterVND.format(Double.parseDouble(obj.getDensotien())) %>"  onchange="submitform()" />
                        </TD>
                        </TR>      					
                </TABLE>                      
        </fieldset>                      
    	</div>
       <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Thu tiền khách hàng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width = "7%">Mã phiếu</TH>
                      <TH align="center" width = "7%">Số chứng từ</TH>
	                    <TH align="center" width = "7%">Ngày </TH>
	                   <TH align="center" width = "6%" style="display:none">CN/Quầy </TH>
	                   <TH align="center" width = "13%">Đối tượng</TH> 
	                    <TH align="center" width = "7%">Thực thu</TH>
	                   <!-- <TH align="center" width = "5%">Tiền tệ</TH> -->
	                   <!-- <TH align="center" width = "7%">Hình thức TT</TH> -->
	                    <TH align="center" width = "10%">Diễn giải</TH>
	                    <TH align="center" width = "5%">Trạng thái</TH>
	                   <!--  <TH align="center" width = "7%"> Ngày sửa </TH> -->
	                    <TH align="center" width = "9%"> Người sửa </TH>
	                    <TH align="center" width = "7%">Tác vụ</TH>
	                </TR>
					<%   int m = 0;		
					if(tthdRs!=null)
					{
						 while(tthdRs.next())
						{ 
							 if((m % 2 ) == 0) { %>
                      			<TR class="tbdarkrow">
	                     	<%}else{ %>
	                          	<TR class="tblightrow">
	                        <%} %> 
	                        
	                        <TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= tthdRs.getString("machungtu")%></TD>
                            <TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= tthdRs.getString("sochungtu")%></TD>
	                        <TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= tthdRs.getString("ngaychungtu") %></TD>
	                        <%-- <TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= tthdRs.getString("NPPCN_FK") %></TD> --%>
	                      
	                      	<% String chinhanh = obj.getChinhanh(tthdRs.getString("NPPCN_FK"));%>
	                      	<TD  style="display:none" align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>> <%=chinhanh%></TD>
	                       
	                        
	                        
	                        <TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= tthdRs.getString("madoituong") %></TD>
	                        <TD align="right" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= formatterVND.format(Double.parseDouble(tthdRs.getString("THUCTHU"))) %>
							<TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %> ><%= tthdRs.getString("ghichu") %></TD>	
							<TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %>>
							<%
								String trangthai = "";
								String tt = tthdRs.getString("trangthai");
								if(tt.equals("0"))
								{	
									
										trangthai = "Chưa duyệt";
									
								}
								else
								{
									if(tt.equals("1"))
									{
										trangthai = "Đã chốt";
									}
									else
									{
										if(tt.equals("2"))
											trangthai = "Đã xóa";
										else
											trangthai = "Đã hủy";
									}
								}
							%>										
							<%= trangthai %>
							<%-- <TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %> ><%= tthdRs.getString("ngaysua") %></TD> --%>
							<TD align="center" <%if(tthdRs.getString("trangthai").equals("2")) {%> style="color: red" <%} %> ><%= tthdRs.getString("nguoisua") %></TD>	
							<TD align="center"> 
								<% if(tt.equals("0")){ %>
									<%if(quyen[2]!=0){ %>
			                            <A href = "../../ErpThutienUpdateSvl?userId=<%=userId%>&update=<%= tthdRs.getString("tthdId") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
			                       	<%} %>
			                       	
		                       	   <%if(quyen[4]!=0){ %>
		                               <A id='chotphieu<%=tthdRs.getString("tthdId")%>'
							       			href=""><img
							       			src="../images/Chot.png" alt="Chốt thu tiền"
							       			width="20" height="20" title="Chốt thu tiền"
							      			border="0" onclick="if(!confirm('Bạn có chắc chốt phiếu thu tiền này?')) {return false ;}else{ processing('<%="chotphieu"+  tthdRs.getString("tthdId")%>' , '../../ErpThutienSvl?userId=<%=userId%>&chot=<%=   tthdRs.getString("tthdId") %>&trangthai=<%= tthdRs.getString("trangthai") %>');}"  >
									   </A>	 													 																			 
																						 
									<%} %>
									
								 <%if(quyen[1]!=0){ %>
	                                <A href="javascript:xoaform(<%= tthdRs.getString("tthdId") %>,<%= tthdRs.getString("trangthai") %> );" >
										 	<img  src="../images/Delete20.png" alt="Xóa thu tiền" width="20" height="20"  border='0' title="Xóa thu tiền"	 >
										</A>
	                             <%} %>		
										 
								 <%}else if (tt.equals("1")){ %>
								 	<A href = "../../ErpThutienUpdateSvl?userId=<%=userId%>&display=<%= tthdRs.getString("tthdId") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
								    <A id='unchot<%=tthdRs.getString("tthdId")%>'
						       			href=""><img
						       			src="../images/unChot.png" alt="Bỏ chốt thu tiền"
						       			width="20" height="20" title="Bỏ chốt thu tiền"
						      			border="0" onclick="if(!confirm('Bạn có chắc bỏ chốt phiếu thu tiền này?')) {return false ;}else{ processing('<%="unchot"+  tthdRs.getString("tthdId")%>' , '../../ErpThutienSvl?userId=<%=userId%>&unchot=<%=   tthdRs.getString("tthdId") %>&trangthai=<%= tthdRs.getString("trangthai") %>');}"  >
								   </A>	 
								 <%} else { %>
				                    <A href = "../../ErpThutienUpdateSvl?userId=<%=userId%>&display=<%= tthdRs.getString("tthdId") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
				                  <%} %>
				                    
							</TD>		
					<% 	m++; }					 
					}	%>
					
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
            </fieldset>
        </div>
    </div>  
</div>
<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
	  TinhTien();
</script>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<%
try{
	if( nccList!=null){
		nccList.close();
	}
	if(khList!=null){
		khList.close();
	}
	if(nvList!=null){
		nvList.close();
	}
	if(htttList!=null){
		htttList.close();
	}
	if(tthdList!=null){
		tthdList.close();
	}
	if(nguoisuaList!=null){
		nguoisuaList.close();
	}
	if(kbhRs!=null){
		kbhRs.close();
	}
	if(tthdRs!=null){
		tthdRs.close();
	}
	obj.DBclose(); 
}catch(Exception er){
	
}

}%>

</form>

</body>
</html>