<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.xoakhachhangtt.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>


<% IErpBangkethutienList obj = (IErpBangkethutienList)session.getAttribute("obj"); %>
<% ResultSet nvgnRs = obj.getNvgnRs(); %>
<% ResultSet ddkdRs = obj.getDdkdRs(); %>
<% ResultSet khuvucRs = obj.getKhuvucRs(); %>
<% ResultSet bangkeRs = obj.getBangkeRs(); 
ResultSet rskhoid=obj.getRskhoid();
%>
<% NumberFormat formater = new DecimalFormat("##,###,###.###");%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpBangkethutienSvl","",userId);
		
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
	    document.forms["ckParkForm"].maphieu.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
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
<form name="ckParkForm" method="post" action="../../ErpBangkethutienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
   
        	&nbsp;Quản lý công nợ > Công nợ phải thu > Bảng kê thu tiền

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
                    
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <TR>
                    <TD class="plainlabel">Nhân viên giao nhận</TD>
                    <TD class="plainlabel">
                    	<select name="nvgnId" class="select2" style="width: 200px;" onchange="submitform();" >
                    		<option value="" >Tất cả</option>
	                       	<% if(nvgnRs != null ) { 
	                       		while( nvgnRs.next() ) {
	                       			if( nvgnRs.getString("pk_seq").equals( obj.getNvgnId() ) ) { %>
	                       				<option value="<%= nvgnRs.getString("pk_seq") %>" selected="selected" ><%= nvgnRs.getString("ten") %></option>
	                       	<% 	} else { %>
	                       				<option value="<%= nvgnRs.getString("pk_seq") %>" ><%= nvgnRs.getString("ten") %></option>
	                       	<% 	} } nvgnRs.close();
	                       	} %>
                       	</select>
                     </TD> 
               
                    <TD class="plainlabel">Trình dược viên</TD>
                    <TD  class="plainlabel">
                        <select name="ddkdId" class="select2" style="width: 200px;" onchange="submitform();" >
                    		<option value="" >Tất cả</option>
	                       	<% if(ddkdRs != null ) { 
	                       		while( ddkdRs.next() ) {
	                       			if( ddkdRs.getString("pk_seq").equals( obj.getDdkdId() ) ) { %>
	                       				<option value="<%= ddkdRs.getString("pk_seq") %>" selected="selected" ><%= ddkdRs.getString("ten") %></option>
	                       	<% 	} else { %>
	                       				<option value="<%= ddkdRs.getString("pk_seq") %>" ><%= ddkdRs.getString("ten") %></option>
	                       	<% 	} } ddkdRs.close();
	                       	} %>
                       	</select>
                     </TD> 
                	</TR> 
                	
                    <TR>
                    	
                        <TD class="plainlabel" >Mã phiếu </TD>
                        <TD class="plainlabel">
                            <input type="text" id="maphieu" name="maphieu" value="<%= obj.getMaPhieu() %>" maxlength="10" onchange="submitform()" />
                        </TD> 
                        
	                    <TD class="plainlabel">Mã/Tên đối tượng</TD>
	                    <TD class="plainlabel">
	                       	<input type="text" name="makhachhang" value="<%= obj.getMakhachhang() %>" >
	                     </TD> 
                    </TR>  
                    
                     <TR>                    	 
	                      <TD  class="plainlabel">Kho hàng hóa</TD>
								<TD  class="plainlabel"  >
								<select name="khohhid" class="select2"  style="width: 200px;" onchange="submitform();">
								
									<option value="">  </option>
									<%if(rskhoid!=null){ while (rskhoid.next()){ %>
									<option value="<%=rskhoid.getString("pk_seq")%>" <%if(rskhoid.getString("pk_seq").equals(obj.getKhohh())){%> selected="selected" <%}%>><%=rskhoid.getString("ten") %>  </option>
									<%} }%>
									
								</select>
						 </TD> 
						 <TD class="plainlabel">
						 	Người tạo
						 </TD>
						 <TD class="plainlabel">
						 	<input name="nguoitao" value="<%= obj.getNguoitao() %>" type="text" onchange="submitform();">
						 </TD>
                    </TR>  
                    
                    <TR> 
						 <TD class="plainlabel">
						 	Số hóa đơn
						 </TD>
						 <TD class="plainlabel" colspan="3">
						 	<input name="sohoadon" value="<%= obj.getSohoadon() %>" type="text" onchange="submitform();">
						 </TD>
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
            	<legend><span class="legendtitle"> Bảng kê thu tiền </span>&nbsp;&nbsp;
            	<%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã phiếu</TH>
	                    <TH align="center">Ngày chứng từ</TH>
	                    <TH align="center">Số tiền bảng kê</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center">Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(bangkeRs != null)
                 		{
                 			int m = 0;
                 			while(bangkeRs.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= bangkeRs.getString("PK_SEQ") %></TD>
		                    <TD align="center"><%= bangkeRs.getString("NGAYCHUNGTU") %></TD>
		                    <TD align="right"><%= formater.format(Double.parseDouble(bangkeRs.getString("TIENTT"))) %></TD>
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = bangkeRs.getString("trangthai");
		                    		int isThuTien = bangkeRs.getInt("isThuTien");
		                    		
		                    		if(tt.equals("0")) //NPP TAO
		                    		{
		                    			trangthai = "Chưa chốt";
		                    		}
		                    		else if(tt.equals("1"))
		                    		{
		                    			trangthai = "Đã chốt";
		                    		}
		                    		else if(tt.equals("2"))
		                    			trangthai = "Đã xóa";
		                    		
		                    		if(isThuTien > 0)
		                    			trangthai = "Đã thu tiền";
		                    		
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= bangkeRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= bangkeRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= bangkeRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= bangkeRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    
		                    <%if(quyen[Utility.SUA]!=0){ %>
                                <A href = "../../ErpBangkethutienUpdateSvl?userId=<%=userId%>&update=<%=bangkeRs.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
 							<% } %>     
 							                           
                             <%if(quyen[Utility.CHOT]!=0){ %>
                                <A href = "../../ErpBangkethutienSvl?userId=<%=userId%>&chot=<%= bangkeRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt bảng kê này?')) return false;"></A>&nbsp;
							<% } %>                                     
                                
                                <%if(quyen[Utility.XOA]!=0){ %>
                              	<A href = "../../ErpBangkethutienSvl?userId=<%=userId%>&delete=<%= bangkeRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng kê này?')) return false;"></A>
                              	<% } %>
                              	                   									
		                    <%} else{ %>
		                    
		                    		  <%if(quyen[Utility.XEM]!=0){ %>
		                    		<A href = "../../ErpBangkethutienUpdateSvl?userId=<%=userId%>&display=<%= bangkeRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    		<% } %>
		                    <% } %>
		                    </TD>
		                </TR>
                     <% m++; } bangkeRs.close(); } %>
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
</body>
</html>
	
<%
try{
	if(nvgnRs != null) nvgnRs.close(); 
	if(ddkdRs != null) ddkdRs.close(); 
	if(khuvucRs != null) khuvucRs.close(); 
	if(bangkeRs != null) bangkeRs.close();
	if(rskhoid != null) rskhoid.close();
	
	obj.DBclose();
	
}catch (Exception ex)
{
	ex.printStackTrace();
}
	}
%>