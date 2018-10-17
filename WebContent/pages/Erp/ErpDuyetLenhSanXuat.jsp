<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuatgiay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>

<% IErpLenhsanxuatList obj = (IErpLenhsanxuatList)session.getAttribute("obj"); %>
<% ResultSet lsxList = (ResultSet)obj.getLsxRs(); %>
<% ResultSet rsdvkd = obj.getDvkdRs(); %>
<% ResultSet nguoitaoRs = obj.getNguoiTaoRs(); %>
<% ResultSet nhamayRs = obj.getNhaMayRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%  
	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
 		 int[] quyen = new  int[5];
		 quyen = util.GetquyenNew("ErpDuyetLenhsanxuatgiaySvl","&phanloai=1",userId);
 		 obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
    
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
     $(document).ready(function() { $(".select2").select2(); });
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
	    document.forms["erpLsxForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpLsxForm"].action.value = "Tao moi";
	    document.forms["erpLsxForm"].submit();
	 }
	 function clearform()
	 {
		
	    document.forms["erpLsxForm"].tungayBD.value = "";
	    document.forms["erpLsxForm"].denngayBD.value = "";
	    document.forms["erpLsxForm"].tungayKT.value = "";
	    document.forms["erpLsxForm"].denngayKT.value = "";
	    document.forms["erpLsxForm"].trangthai.value = "";
	    document.forms["erpLsxForm"].dvkdid.value = "";
	    document.forms["erpLsxForm"].tensanpham.value = "";
	    document.forms["erpLsxForm"].ghichu.value = "";
	    document.forms["erpLsxForm"].solsx.value = "";
	    document.forms["erpLsxForm"].nguoitao.value = "";
	    document.forms["erpLsxForm"].nhamay.value = "";
	    document.forms["erpLsxForm"].submit();
	 }
	 
	 function xuatExcel()
	 {
		/*  if(document.getElementById("tungayBD").value == "" || document.getElementById("tungayKT").value == "")
		 {
			 alert('Vui lòng chọn khoảng thời gian ngày bắt đầu sản xuất');
			 return;
		 } */
		 document.forms["erpLsxForm"].action.value = "Excel";
		 document.forms["erpLsxForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpLsxForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpLsxForm"].msg.value);
	 }
	 
	 function XacNhanChuyen(PlainId)
	 {
		var kbsxId = document.getElementById('KbsxId' + PlainId).value;
		if(kbsxId == '')
		{
			alert('Sản phẩm này chưa có kịch bản sản xuất');
			return;
		}
		
		//alert('PlaintId: ' + PlainId);
		//alert('KbsxId: ' + kbsxId);
		document.forms["erpLsxForm"].lxsId.value = PlainId;
		document.forms["erpLsxForm"].kbsxId.value = kbsxId;
		document.forms["erpLsxForm"].action.value = "kichhoat";
		
		document.forms["erpLsxForm"].submit();
		
	 }
	 
	</SCRIPT>
</head>
<body>
<form name="erpLsxForm" method="post" action="../../ErpDuyetLenhsanxuatgiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="phanloai" value='<%= obj.getPhanLoai() %>'>

<input type="hidden" name="lxsId" value="" >
<input type="hidden" name="kbsxId" value="" >

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Sản xuất >Duyệt lệnh sản xuất
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
                   
					<%-- <TR >
                        <TD width="20%" class="plainlabel" >Ngày bắt đầu </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungaySX" name="tungaySX" value="<%= obj.getTungayTao() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                    <TR >
                        <TD width="20%" class="plainlabel" >Ngày kết thúc </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungayDk" name="tungayDk" value="<%= obj.getTungayDk() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR> --%>
                    
                    <TR >
                        <TD width="10%" class="plainlabel" >Ngày bắt đầu: </TD>
                         <TD width="10%" class="plainlabel" >Từ ngày </TD>
                        <TD width="20%" class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungayBD" name="tungayBD" value="<%= obj.getTungayBD() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                         <TD width="10%" class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngayBD" name="denngayBD" value="<%= obj.getDenngayBD() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR >
                        <TD width="10%" class="plainlabel" >Ngày kết thúc: </TD>
                         <TD width="10%" class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungayKT" name="tungayKT" value="<%= obj.getTungayKT() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                         <TD  class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngayKT" name="denngayKT" value="<%= obj.getDenngayKT() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     	
                        <TD class="plainlabel" valign="middle" colspan="2">Trạng thái </TD>
                        <TD class="plainlabel"  valign="middle">
                           <select name="trangthai" onchange="submitform();" > 
                           		<option value="">  </option>
                   		    	<%  
 								String[] mang=new String[]{"0","1","2","3","4","5","6","7"};
 								String[] mangten=new String[]{"Chưa kích hoạt","Chưa kích hoạt - Đã yêu cầu chuyển kho" , 
 										"Đã kích hoạt","Đã nhận thành phẩm","Đã kiểm định chất lượng"," Đã tiêu hao nguyên liệu","Hoàn tất","Đã hủy"};
 								 for(int j=0;j<mang.length;j++){
 									 if(obj.getTrangthai().trim().equals(mang[j])){
 									 %>
 									 <option selected="selected" value="<%=mang[j]%>"> <%=mangten[j] %> </option>
 									 <%
 									 }else{
 									 %>
 									 <option value="<%=mang[j]%>"> <%=mangten[j] %> </option>
 									 <% 
 									 }
 								 }
 								%>
                            
                           </select>
                        </TD>
                        <td class="plainlabel" >Đơn vị kinh doanh</td>
               		 <td class="plainlabel"  > 
                		<select id="dvkdid" name="dvkdid" onchange="submitform()">
                    		<option value=""> </option>
                        	<%
                        		if(rsdvkd != null)
                        		{
                        			try
                        			{
                        			while(rsdvkd.next())
                        			{  
                        			if( rsdvkd.getString("pk_seq").equals(obj.getIddvkd())){ %>
                        				<option value="<%= rsdvkd.getString("pk_seq") %>" selected="selected" ><%=    rsdvkd.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= rsdvkd.getString("pk_seq") %>" ><%=  rsdvkd.getString("ten") %></option>
                        		 <% } } rsdvkd.close();
                        		 	} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                	 </td>                         
                    </TR>  
                    <TR>
                   	 <TD class="plainlabel" colspan="2">Tên sản phẩm</TD>
                        <TD class="plainlabel"  >
                            <input type="text" onchange="submitform();" id="tensanpham" name="tensanpham" value="<%=obj.getTenSp() %>" />
                        </TD>
                        <TD class="plainlabel">Số lệnh sản xuất </TD>
                        <TD class="plainlabel" >
                            <input type="text"  onchange="submitform();"
                                   id="solsx" name="solsx" value="<%=obj.getLSXId() %>" />
                        </TD>
                    </TR> 
                    <TR>
                    	<TD class="plainlabel"  colspan="2">Người tạo </TD>                        
                         <TD class="plainlabel" width="200px">
                          <select class="select2" name="nguoitao" id="nguoitao" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	 <%
                        		if(nguoitaoRs != null)
                        		{
                        			try
                        			{
                        			while(nguoitaoRs.next())
                        			{  
                        			if( nguoitaoRs.getString("pk_seq").equals(obj.getNguoitaoId())){ %>
                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" selected="selected" ><%= nguoitaoRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" ><%= nguoitaoRs.getString("ten") %></option>
                        		 <% } } nguoitaoRs.close();} catch(SQLException ex){}
                        		}
                        	%> 
                          </select>
                        </TD>
                        <TD class="plainlabel"  >Xưởng </TD>                        
                         <TD class="plainlabel" width="200px">
                          <select class="select2" name="nhamay" id="nhamay" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	 <%
                        		if(nhamayRs != null)
                        		{
                        			try
                        			{
                        			while(nhamayRs.next())
                        			{  
                        			if( nhamayRs.getString("pk_seq").equals(obj.getNhamayId())){ %>
                        				<option value="<%= nhamayRs.getString("pk_seq") %>" selected="selected" ><%= nhamayRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nhamayRs.getString("pk_seq") %>" ><%= nhamayRs.getString("ten") %></option>
                        		 <% } } nhamayRs.close();} catch(SQLException ex){}
                        		}
                        	%> 
                          </select>
                        </TD>
                    </TR>
                    
                     
                  <TR>
                        <TD class="plainlabel" colspan="2">Ghi chú</TD>
                        <TD class="plainlabel"  colspan="4">
                            <input type="text" onchange="submitform();" id="ghichu" name="ghichu" value="<%=obj.getGhichu() %>" />
                        </TD>
                    </TR> 
                
                    <tr>
                        <td colspan="7" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:xuatExcel()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Xuất file Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;   
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Lệnh sản xuất </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                			<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="7%">Số lệnh </TH>
	              		  <TH align="center" width="7%">Ngày bắt đầu </TH>
	                   
	                    <TH align="center" width="20%">Sản phẩm</TH>
	                    <TH align="center" width="9%"> Trạng thái</TH>
	                    <TH align="center" width="10%"> Công đoạn thực hiện</TH>
	                    <TH align="center" width="8%"> Ngày tạo</TH>
	                    <TH align="center" width="8%"> Người tạo </TH>
	                    <TH align="center" width="8%">Ngày sửa </TH>
	                    <TH align="center" width="8%"> Người sửa </TH>
	                    <TH align="center" width="12%" >Tác vụ</TH>
	                </TR>
					<%
						int count = 1;
                 		if(lsxList != null)
                 		{
                 			int m = 0;
                 			while(lsxList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                        
		                       
		                        
		                    <% if(lsxList.getString("dondathang_fk").equals("-1")) { %>
			                    <TD align="center"><%= lsxList.getString("pk_seq") + (lsxList.getString("diengiai").length() >0? " ("+lsxList.getString("diengiai")+")" :""  )%></TD>
			                    <TD align="center"><%= lsxList.getString("ngaybatdau") %></TD>
		                    <% } else { %>
		                    	<TD align="center" style="color: red;"><%= lsxList.getString("pk_seq") %></TD>
			                  <TD align="center"><%= lsxList.getString("ngaybatdau") %></TD>
		                    <% } %>
		                    
		                  <%--   <TD align="center" ><%= lsxList.getString("ngaydukienHT") %></TD>	 --%>
		                    <TD ><%= lsxList.getString("spTen") %></TD>
		                    	<% String tt = lsxList.getString("trangthai");
		                    	if(tt.equals("7")){%>  
		                    	 <TD align="center" style="color:red">
		                    	 <%}
		                    	else 
		                    	{%>
		                    		<TD align="center" >
		                    	<% }%>
		                    	<%
		                    		String trangthai = "";
		                    		
		                    		 for(int j=0;j<mang.length;j++){
		 									 if(tt.trim().equals(mang[j])){
		 										trangthai=mangten[j];
		 									 }
		 								 }
		 							 
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   
		                    <TD align="center"><%= lsxList.getString("congdoan") %></TD>						                                    
					     	<TD align="center"><%= lsxList.getString("Ngaytao") %></TD>	
		                    <TD ><%= lsxList.getString("NguoiTao") %></TD>
         					<TD align="center"><%= lsxList.getString("NgaySua") %></TD>
							<TD ><%= lsxList.getString("NguoiSua") %></TD>
									
									
									
									
		                    <TD align="center"> 
		                    		<% if(obj.getPhanLoai().equals("1")) {%>
	                       			 <A href = "../../ErpDuyetLenhsanxuatgiaySvl?userId=<%=userId%>&duyetlsx=<%= lsxList.getString("PK_SEQ") %>&phanloai=1"><img src="../images/Chot.png" alt="Kích hoạt lệnh" title="Kích hoạt lệnh sản xuất" width="20" height="25" border=0 onclick="if(!confirm('Bạn có muốn kích hoạt lệnh sản xuất này?')) return false;"></A>
	                       			 <A href = "../../ErpDuyetLenhsanxuatgiaySvl?userId=<%=userId%>&xoalsx=<%= lsxList.getString("PK_SEQ") %>&phanloai=1"><img src="../images/Delete20.png" alt="Xóa lệnh" title="Xóa lệnh sản xuất" width="20" height="25" border=0 onclick="if(!confirm('Bạn có muốn xóa lệnh sản xuất này?')) return false;"></A>
	                       			 <A href = "../../ErpLenhsanxuatgiayUpdateSvl?userId=<%=userId%>&display=<%= lsxList.getString("PK_SEQ") %>&phanloai=1"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    	  
	                       			 <%}else { %>
	                       			 <A href = "../../ErpDuyetLenhsanxuatgiaySvl?userId=<%=userId%>&duyetkh=<%= lsxList.getString("PK_SEQ") %>&phanloai=2"><img src="../images/Chot.png" alt="Kích hoạt lệnh" title="Kích hoạt lệnh sản xuất" width="20" height="25" border=0 onclick="if(!confirm('Bạn có muốn kích hoạt lệnh sản xuất này?')) return false;"></A>
	                       			 <A href = "../../ErpDuyetLenhsanxuatgiaySvl?userId=<%=userId%>&xoalsx=<%= lsxList.getString("PK_SEQ") %>&phanloai=2"><img src="../images/Delete20.png" alt="Xóa lệnh" title="Xóa lệnh sản xuất" width="20" height="25" border=0 onclick="if(!confirm('Bạn có muốn xóa lệnh sản xuất này?')) return false;"></A>
	                       			 <A href = "../../ErpLenhsanxuatgiayUpdateSvl?userId=<%=userId%>&display=<%= lsxList.getString("PK_SEQ") %>&phanloai=2"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    	  
	                       			 <%} %>
			                
		                    </TD>
		                    
		                </TR>
                     <% m++; }  } %>
                     
                     
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
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">
 <% for(int i = 1; i <= count; i++) { %>
		dropdowncontent.init('PlnId<%= i %>', "left-top", 300, "click");
 <% }
 
 %>
</script>
<%
try{
	 
	if(lsxList!=null){
		lsxList.close();
	}
	
 	
}catch(Exception er){
	
}finally{
	obj.DBclose();
	
 	session.setAttribute("obj",null);
 	session.setAttribute("obj", null) ;  ; 
}
} %>
</body>
</html>