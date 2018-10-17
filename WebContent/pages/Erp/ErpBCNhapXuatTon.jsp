<%@page import="java.util.concurrent.ExecutionException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>
<%@ page  import = "geso.traphaco.erp.servlets.baocao.XuatNhapTonObj" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>

<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanPhamRs(); %>
<% ResultSet doituongRs = (ResultSet)obj.getDoituongRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% List<XuatNhapTonObj> listXNT = (List<XuatNhapTonObj>)session.getAttribute("ListXNT");  %>
<% String ListXNT_check = (String) session.getAttribute("ListXNT_check"); %>
<% String pattern = "###,###.###";
   DecimalFormat formatter = new DecimalFormat(pattern); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
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
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("khoId").value == "")
		 {
			 alert('Vui lòng chọn kho lấy báo cáo');
			 return;
		 }

		 document.forms["erpDmhForm"].action.value = "submit";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 function submitweb()
	 { 
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 var dinhdang = document.getElementById("dinhdang").value;
		 if( document.getElementById("khoId").value == "" && dinhdang != '3' )
		 {
			 alert('Vui lòng chọn kho lấy báo cáo');
			 return;
		 }

		 document.forms["erpDmhForm"].action.value = "web";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 
	 function locsanpham()
	 {   
		 document.forms["erpDmhForm"].action.value = "search";
	     document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
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
<form name="erpDmhForm" method="post" action="../../ErpXuatnhaptonTTSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý tồn kho > Báo cáo > Báo cáo xuất nhập tồn
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
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>

                    <TR>
                        <TD class="plainlabel" valign="middle" >Kho </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="khoId" id="khoId" onchange="locsanpham()" class="select2" style="width: 600px"  >
                            	<option value="">Tất cả</option>
                            	<%
	                        		if(khoRs != null)
	                        		{
	                        			while(khoRs.next())
	                        			{  
	                        			if( khoRs.getString("pk_seq").equals(obj.getKhoIds())){ %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
	                        		 <% } } khoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Đối tượng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="doituongId" id="doituongId" class="select2" style="width: 600px"  >
                            	<option value="">Tất cả</option>
                            	<%
	                        		if(doituongRs != null)
	                        		{
	                        			while(doituongRs.next())
	                        			{  
	                        			if( doituongRs.getString("pk_seq").equals(obj.getDoituongId())){ %>
	                        				<option value="<%= doituongRs.getString("pk_seq") %>" selected="selected" ><%= doituongRs.getString("Ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= doituongRs.getString("pk_seq") %>" ><%= doituongRs.getString("Ten") %></option>
	                        		 <% } } doituongRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>
                     
                    <TR>
                        <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                        	<select name = "loaisanpham"   id="loaisanpham" onchange="locsanpham()" class="select2" style="width: 600px" multiple="multiple" >
                         		<option value="" >Tất cả</option>
	                        	<%
	                        		if(lspRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(lspRs.next())
	                        			{  
	                        			if( obj.getLoaiSanPhamIds().contains(lspRs.getString("pk_seq"))  ){ %>
	                        				<option value="<%= lspRs.getString("pk_seq") %>" selected="selected" ><%= lspRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspRs.getString("pk_seq") %>" ><%= lspRs.getString("ten") %></option>
	                        		 <% } } lspRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>                            
                        </TD>                        
                    </TR> 
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                         	<select name = "spIds"   id="spIds"  class="select2" style="width: 600px" multiple="multiple" >
                         		<option value="" >Tất cả</option>
	                        	<%
	                        		if(spRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(spRs.next())
	                        			{  
	                        			if( obj.getSanPhamIds().contains(spRs.getString("pk_seq"))  ){ %>
	                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ma") + ", " + spRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ma") + ", " + spRs.getString("ten") %></option>
	                        		 <% } } spRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>                
                        </TD>                        
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Theo mức </TD>
                        <TD class="plainlabel" valign="middle" >
                         	<select name = "dinhdang" id="dinhdang"  class="select2" style="width: 200px"  >
                         		<% if( obj.getXemtheolo().equals("3") ) { %>
                         			<option value="3" selected="selected" >Theo hóa đơn</option>
                         		<% } else { %>
                         			<option value="3" >Theo hóa đơn</option>
                         		<% } %>
                         		<% if( obj.getXemtheolo().equals("1") ) { %>
                         			<option value="1" selected="selected" >Theo lô</option>
                         		<% } else { %>
                         			<option value="1" >Theo lô</option>
                         		<% } %>
                         		
                         		
                         		<% if( obj.getXemtheolo().equals("2") ) { %>
                         			<option value="2" selected="selected" >Chi tiết</option>
                         		<% } else { %>
                         			<option value="2" >Chi tiết</option>
                         		<% } %>
                         		<% if( obj.getXemtheolo().equals("0") ) { %>
                         			<option value="0" selected="selected" >Tổng quát</option>
                         			<% } else { %>
                         			<option value="0" >Tổng quát</option>
                         		<% } %>
                         		
                         		
                         		<% if( obj.getXemtheolo().equals("4") ) { %>
                         			<option value="4" selected="selected" >Theo mã phiếu </option>
                         			<% } else { %>
                         			<option value="4" >Theo mã phiếu</option>
                         		<% } %>
                         	
	                        	
	                    	</select>                
                        </TD>                        
                    </TR>
               
                     <tr>
                        <td  class="plainlabel">
                            <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                    
                        <td  class="plainlabel">
                            <a class="button" href="javascript:submitweb();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Xem trên web </a>
                        </td>
                    </tr> 
                    
                    </TABLE>                  
       </fieldset>            					                    
    	</div>
    	
    	
    	<!-- phần dành cho hiển thị của web -->
    	<% if(ListXNT_check!=null){ %>
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Kết quả  </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
            	
					<TR class="tbheader">
	                    <TH align="center" width="8%">Mã SP</TH>
	                    <TH align="center" width="8%">Tên SP</TH>
	                 	<TH align="center" width="12%">Đơn vị </TH>  
	                 	
	                 	<% if(obj.getXemtheolo().equals("1")){  %>
		                    <TH align="center" width="12%">Số lô</TH> 
		                    <TH align="center" width="10%">Ngày hết hạn</TH>
	                    <%} %>
	                    
	                  <% if(obj.getXemtheolo().equals("4")){  %>
	                   		<TH align="center" width="12%">Mã phiếu</TH> 
		                    <TH align="center" width="12%">Số lô</TH> 
		                    <TH align="center" width="10%">Ngày hết hạn</TH>
	                    <%} %>
	                    
	                    <% if(obj.getXemtheolo().equals("2")){  %>
		                    <TH align="center" width="8%">Mã mẻ</TH>
		                    <TH align="center" width="8%">Mã thùng</TH>
		                    <TH align="center" width="10%">Vị trí</TH>
		                    <TH align="center" width="10%">Mã phiếu</TH>
		                    <TH align="center" width="10%" >Phiếu định tính</TH>
		                    <TH align="center" width="10%" >Phiếu EO</TH>
		                    <TH align="center" width="10%" >Mã marquette</TH>
		                    <TH align="center" width="10%" >Hàm lượng</TH>
		                    <TH align="center" width="10%" >Hàm ẩm</TH>
		                     <TH align="center" width="10%" >NSX</TH>
	                    <%} %>
	                    
	                    <TH align="center" width="10%" >Đầu kỳ</TH>
	                    <TH align="center" width="10%" >Nhập</TH>
	                    <TH align="center" width="10%" >Xuất</TH>
	                    <TH align="center" width="10%" >Tồn</TH>
	                </TR>
	                
	                <%
	                	if(listXNT!=null){
	                		for(int i=0; i< listXNT.size();i++){
	                			XuatNhapTonObj temp = listXNT.get(i);
	                			
	                			if((i % 2 ) == 0) {%>
	                         	<TR class='tbdarkrow'>
	                        <%}else{ %>
	                          	<TR class='tblightrow'>
	                        <%} %>
								<TD align="center"><%=temp.getMa() %></TD>
								<TD align="center"><%=temp.getTen() %></TD>
								<TD align="center"><%=temp.getDonvi() %></TD>

								 <% if(obj.getXemtheolo().equals("1")){  %>
									<TD align="center"><%=temp.getSolo() %></TD>
									<TD align="center"><%=temp.getNgayhethan() %></TD>
								<%} %>
								
								
								<% if(obj.getXemtheolo().equals("4")){  %>
								<TD align="center"><%=temp.getMaphieu() %></TD>
									<TD align="center"><%=temp.getSolo() %></TD>
									<TD align="center"><%=temp.getNgayhethan() %></TD>
								<%} %>
								
								 <% if(obj.getXemtheolo().equals("2")){  %>
									<TD align="center"><%=temp.getMame() %></TD>
									<TD align="center"><%=temp.getMathung() %></TD>
									<TD align="center"><%=temp.getVitri() %></TD>
									<TD align="center"><%=temp.getMaphieu() %></TD>
									<TD align="center"><%=temp.getPhieuDT() %></TD>
									<TD align="center"><%=temp.getPhieuEO() %></TD>
									<TD align="center"><%=temp.getMarq() %></TD>
									<TD align="center"><%=temp.getHamluong()  %></TD>
									<TD align="center"><%=temp.getHamam()  %></TD>
									<TD align="center"><%=temp.getNsx()  %></TD>
								<%} %>
								
								<TD align="center"><%= formatter.format(temp.getDauky())  %></TD>
								<TD align="center"><%= formatter.format(temp.getNhap())  %></TD>
								<TD align="center"><%= formatter.format(temp.getXuat())  %></TD>
								<TD align="center"><%= formatter.format(temp.getToncuoi())  %></TD>
								
							</TR>	
	                		<%}
	                	}
	                %>
	                
	                
				</TABLE>
            </fieldset>
        </div>
        <%} %>
    </div>  
</div>
</form>
</body>
<%

try{
	if(spRs!=null){
		spRs.close();
	}
	obj.close();
	session.setAttribute("obj", null);
	session.removeAttribute("ListXNT");
	if(ListXNT_check!=null)
		session.removeAttribute("ListXNT_check");
}catch(Exception err){
	
}

%>
</html>