<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.yeucauchuyenkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.sql.SQLException"%>
<% IErpCannguyenlieuList obj = (IErpCannguyenlieuList)session.getAttribute("obj"); %>
<% ResultSet lsxList = (ResultSet)obj.getLsxRs(); %>
 
<% ResultSet khonhanList = (ResultSet)obj.getKhonhanRs(); %> 
<% ResultSet khoChuyenList = (ResultSet)obj.getKhoChuyenRs(); %> 
<% ResultSet nvList = (ResultSet)obj.getNhanvienRs(); %>
<% ResultSet nv2List = (ResultSet)obj.getNhanvien2Rs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%  

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpCannguyenlieuSvl","",userId);

obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Canfoco - Project</title>
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
	    document.forms["erpLsxForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpLsxForm"].action.value = "Tao moi";
	    document.forms["erpLsxForm"].submit();
	 }
	 
	 function duyetform(Id, loai)
	 {
	 	 if(!confirm('Bạn có muốn chốt chuyển kho này ?')) 
	 	 {
	 		 return false ;
	 	 }
	 	 document.forms['erpLsxForm'].chungtu.value = Id;
	 	 document.forms['erpLsxForm'].action.value= 'chot';
	 	 document.forms['erpLsxForm'].submit();
	 }
	 
	 function boduyetform(Id, loai)
	 {
	 	 if(!confirm('Bạn có muốn bỏ chốt yêu cầu chuyển kho này không?')) 
	 	 {
	 		 return false ;
	 	 }

	 	 document.forms['erpLsxForm'].chungtu.value = Id;
	 	 document.forms['erpLsxForm'].action.value= 'bochot';
	 	 document.forms['erpLsxForm'].submit();
	 }
	 
	 function clearform()
	 {   
	    document.forms["erpLsxForm"].sophieu.value = "";
	    document.forms["erpLsxForm"].tungaySX.value = "";
	    document.forms["erpLsxForm"].sohoadon.value = "";
	    document.forms["erpLsxForm"].denngaySX.value = "";
	    <%if(obj.getIsnhanHang().equals("LSX")){ %>
	    document.forms["erpLsxForm"].khonhanId.value = "";

	    document.forms["erpLsxForm"].solenhsx.value = "";
	    <%}%>
	    document.forms["erpLsxForm"].khochuyenId.value = "";
	    document.forms["erpLsxForm"].trangthai.value = "";
	    document.forms["erpLsxForm"].nguoitao.value = "";
	    document.forms["erpLsxForm"].nguoisua.value = "";
	    document.forms["erpLsxForm"].sochungtubn.value = "";
	    document.forms["erpLsxForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpLsxForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpLsxForm"].msg.value);
	 	}
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#nguoitao,#nguoisua,#khonhanId,#trangthai,#khochuyenId").select2(); });
     
 </script>
</head>
<body>
<form name="erpLsxForm" method="post" action="../../ErpCannguyenlieuSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="task" value="<%=obj.getIsnhanHang()%>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="chungtu" id="chungtu"  >
<input type="hidden" name="isnhanHang" value="<%= obj.getIsnhanHang() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Cân nguyên liệu 
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
                        <TD class="plainlabel" width="10%">Ngày cân:</TD>
                        <TD class="plainlabel" width="10%">Từ ngày </TD>
                        <TD class="plainlabel" width="200px">
                            <input type="text" class="days" onchange="submitform()"
                                   id="tungaySX" name="tungaySX" value="<%= obj.getTungayTao() %>" maxlength="10"  />
                        </TD>
                        
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" colspan="4">
                            <input type="text" class="days" onchange="submitform()"
                                   id="denngaySX" name="denngaySX" value="<%= obj.getDenngayTao() %>" maxlength="10" />
                        </TD>
                    </TR>
                    
                     <TR>
                     	<TD class="plainlabel">&nbsp;</TD>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onchange="submitform()" style="width: 200px">
                           	<option value="">  </option>
                   		    <%  
 								String[] mang = new String[]{"0", "1", "2", "3", "4"};
 								String[] mangten = new String[]{"Chưa chốt", "Đã chốt", "Đã nhận hàng", "Hoàn tất", "Đã hủy" };
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
                        
                        <TD class="plainlabel" width="10%">Số phiếu </TD>
                        <TD class="plainlabel" width="200px" colspan="4">
                            <input type="text"  onchange="submitform()"
                                   id="sophieu" name="sophieu" value="<%= obj.getSophieu() %>" maxlength="10"  />
                        </TD>                       
                    </TR> 
                    <TR>
                        <TD class="plainlabel" width="10%">&nbsp;</TD>
                         <TD class="plainlabel" width="10%">Kho cân </TD>
                         <TD class="plainlabel" width="200px" colspan="5">
                            <select name="khochuyenId" id="khochuyenId" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(khoChuyenList != null)
                        		{
                        			try
                        			{
                        			while(khoChuyenList.next())
                        			{  
                        			if( khoChuyenList.getString("pk_seq").equals(obj.getKhoChuyenId())){ %>
                        				<option value="<%= khoChuyenList.getString("pk_seq") %>" selected="selected" ><%= khoChuyenList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khoChuyenList.getString("pk_seq") %>" ><%= khoChuyenList.getString("ten") %></option>
                        		 <% } } khonhanList.close();} catch(SQLException ex){}
                        		}
                        	%>
                          </select>
                        </TD>  
                        <%-- <%} %> --%>
                        <%-- <TD class="plainlabel" >Lý do </TD>
                        <TD class="plainlabel">
                            <input type="text" onchange="submitform()"
                                   id="lydo" name="lydo" value="<%= obj.getLydo() %>" />
                        </TD> --%>                        
                    </TR>

                    <tr>
                        <td colspan="6" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                         <td colspan="6" class="plainlabel"></td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Cân nguyên liệu   </span>&nbsp;&nbsp;
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="10%">Số phiếu</TH>
	                    <TH align="center" width="10%">Ngày cân</TH>
	                 	<TH align="center" width="15%">Kho </TH>  
	                    <TH align="center" width="15%">Sản phẩm</TH> 
	                    <TH align="center" width="10%">Số lô</TH>
	                    <TH align="center" width="10%">Trạng thái</TH>
	                    <!-- <TH align="center" width="8%">Ngày tạo</TH>
	                    <TH align="center" width="8%"> Người tạo</TH> -->
	                    <TH align="center" width="10%">Ngày sửa</TH>
	                    <TH align="center" width="10%">Người sửa</TH>
	                    <TH align="center" width="10%" >Tác vụ</TH>
	                </TR>
					<%
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
		                    <TD align="center"><%= lsxList.getString("pk_seq") %></TD>
		                    <TD align="center"><%= lsxList.getString("ngaycan") %></TD>
		                    <TD align="center"><%= lsxList.getString("khoTen") %></TD>
		                 	<TD align="left"><%= lsxList.getString("spTen") %></TD> 
		                   	<TD align="left"><%= lsxList.getString("solo") %></TD> 
		                    <%
		                    String trangthai = "";
                    		String tt = lsxList.getString("trangthai");
		                    %>
		                    <%if(tt.equals("4")){ %>
		                    <TD align="center" style="color: red">
		                    <%}else{ %>
		                    <TD align="center">
		                    	<%}
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else if(tt.equals("1"))
		                    			trangthai = "Đã chốt";
		                    		else if(tt.equals("2")) 
		                    			trangthai = "Đã hủy";
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<%-- <TD align="center"><%= lsxList.getString("Ngaytao") %></TD>	
		                    <TD ><%= lsxList.getString("NguoiTao") %></TD> --%>
         					<TD align="center"><%= lsxList.getString("NgaySua") %></TD>
							<TD ><%= lsxList.getString("NguoiSua") %></TD>
									
		                    <TD align="center"> 

		                  	  <% if(tt.equals("0")){ %> 
		                    
		                    	<%if(quyen[2]!=0){ %>
                                	<A href = "../../ErpCannguyenlieuUpdateSvl?userId=<%=userId%>&update=<%=lsxList .getString("PK_SEQ") %>&task=<%=obj.getIsnhanHang()%>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                 <%if(quyen[4]!=0){ %>
	                                <A href="javascript:duyetform(<%= lsxList.getString("PK_SEQ") %>, '<%= obj.getIsnhanHang() %>' );" >
									 	<img  src="../images/Chot.png" alt="Chốt chuyển kho" width="20" height="20"  border='0' title="Chốt chuyển kho"	 >
									</A>
                                <%} %>
                                 <%if(quyen[1]!=0){ %>
                                	<A href = "../../ErpCannguyenlieuSvl?userId=<%=userId%>&delete=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.getIsnhanHang()%>"><img src="../images/Delete20.png" alt="Xóa chuyển kho" title="Xóa chuyển kho" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa yêu cầu chuyển kho này không?')) return false;"></A>
                                <%} %>	
                                								
		                    <% } else {  %>
		                    	<A href = "../../ErpCannguyenlieuUpdateSvl?userId=<%=userId%>&display=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.getIsnhanHang()%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" border=0></A>&nbsp;	
		                    <% } %> 
		                    
		                    </TD>
		                </TR>
                     <% m++; } lsxList.close(); } %>
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
												
												//System.out.println("Current page:" + obj.getNxtApprSplitting());
												//System.out.println("List page:" + listPage[i]);
												
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
<% 
try{
	if(lsxList!=null){
		lsxList.close();
	}
	if(khoChuyenList!=null){
		khoChuyenList.close();
	}
 
	
	obj.DBclose();
	session.setAttribute("obj",null);
}catch(Exception er){
	
}
	
	} %>
</body>
</html>