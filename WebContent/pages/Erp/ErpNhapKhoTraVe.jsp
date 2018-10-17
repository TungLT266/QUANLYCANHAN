<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.yeucauchuyenkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpNhapkhotraveList obj = (IErpNhapkhotraveList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getNhapkhoRs(); %>
<% ResultSet khonhapRs = obj.getKhonhanRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("ErpNhapkhotraveSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoDMS/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpNhapkhotraveSvl","",userId);
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
	    document.forms["ckParkForm"].soPhieu.value = "";
	    document.forms["ckParkForm"].masanpham.value = "";
	    document.forms["ckParkForm"].sohoadon.value = "";
	    document.forms["ckParkForm"].khonhanId.value = "";
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
<form name="ckParkForm" method="post" action="../../ErpNhapkhotraveSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Nhập kho hàng trả về
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
                        <TD class="plainlabel" width="15%">Từ ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" name="tungay" value="<%= obj.getTungayTao() %>" class="days" maxlength="10" onchange="submitform();" />
                        </TD>
                        
                        <TD class="plainlabel" width="15%">Đến ngày</TD>
                        <TD class="plainlabel" colspan="3">
                            <input type="text" class="days" 
                                   name="denngay" value="<%= obj.getDenngayTao() %>" class="days" maxlength="10" onchange="submitform();"  />
                        </TD>
                    </TR>								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Mã sản phẩm</TD>
                        <TD class="plainlabel">
                            <input type="text" name="masanpham" value="<%= obj.getMasanpham() %>"  onchange="submitform();" />
                        </TD>
                        <TD class="plainlabel" width="15%">Số hóa đơn</TD>
                        <TD class="plainlabel" colspan="3">
                            <input type="text" name="sohoadon" value="<%= obj.getSohoadon() %>"  onchange="submitform();" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" width="15%">Mã số</TD>
                        <TD class="plainlabel">
                            <input type="text" name="soPhieu" value="<%= obj.getSophieu() %>"  onchange="submitform();" />
                        </TD>
                        <TD class="plainlabel" >Kho nhận </TD>
			            <TD class="plainlabel" colspan="3"  >
			                    	<select name = "khonhanId" style="width: 350px;" onchange="submitform();" class="select2"  >
			                    		<option value=""> </option>
			                        	<%
			                        		if(khonhapRs != null)
			                        		{
			                        			try
			                        			{
			                        			while(khonhapRs.next())
			                        			{  
			                        			if( khonhapRs.getString("pk_seq").equals(obj.getKhonhanId())){ %>
			                        				<option value="<%= khonhapRs.getString("pk_seq") %>" selected="selected" ><%= khonhapRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= khonhapRs.getString("pk_seq") %>" ><%= khonhapRs.getString("ten") %></option>
			                        		 <% } } khonhapRs.close();} catch(Exception ex){ }
			                        		}
			                        	%>
			                    	</select>
			                    </TD>
                    </TR>
                    
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="4">
                           <select class="select2" style="width: 200px;" name="trangthai" onchange="submitform();" >
                           		<option value=""> </option>
								<% if( obj.getTrangthai().equals("1") ) { %>
									<option value="1" selected="selected" >Chờ nhận hàng</option>
								<% } else { %>
									<option value="1" >Chờ nhận hàng</option>
								<% } %>
								<% if( obj.getTrangthai().equals("2") ) { %>
									<option value="2" selected="selected" >Đã nhận</option>
								<% } else { %>
									<option value="2" >Đã nhận</option>
								<% } %>
								<% if( obj.getTrangthai().equals("3") ) { %>
									<option value="3" selected="selected" >Hoàn tất</option>
								<% } else { %>
									<option value="3" >Hoàn tất</option>
								<% } %>
								<% if( obj.getTrangthai().equals("4") ) { %>
									<option value="4" selected="selected" >Không nhận hàng</option>
								<% } else { %>
									<option value="4" >Không nhận hàng</option>
								<% } %>
                           </select>
                        </TD>                        
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel" >
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
            	<legend><span class="legendtitle"> Nhập kho hàng trả về </span>&nbsp;&nbsp;
            	<%-- <%if(quyen[Utility.THEM]!=0) {%> 
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %> --%>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%;" >Mã số</TH>
	                    <TH align="center" style="width: 8%;" >Số hóa đơn</TH>
	                    <TH align="center" style="width: 8%;" >Ngày trả</TH>
	                    <TH align="center" style="width: 23%;" >Đối tượng</TH>
	                    <TH align="center" style="width: 18%;" >Ghi chú</TH>
	                    <TH align="center" style="width: 7%;" >Trạng thái</TH>
	                    <!-- <TH align="center" style="width: 10%;" > Ngày tạo</TH>
	                    <TH align="center" style="width: 10%;" > Người tạo </TH> -->
	                    <TH align="center" style="width: 10%;" > Ngày sửa </TH>
	                    <TH align="center" style="width: 10%;" > Người sửa </TH>
	                    <TH align="center" style="width: 10%;" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= nhapkhoRs.getString("PK_SEQ") %></TD>
		                     <TD align="center"><%= nhapkhoRs.getString("sohoadon") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("ngaytra") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("doituong") %></TD>  
		                    <TD align="center"><%= nhapkhoRs.getString("ghichu") %></TD> 
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		if(tt.equals("1"))
		                    			trangthai = "Chờ nhận hàng";
		                    		else
		                    		{
		                    			if(tt.equals("2"))
		                    				trangthai = "Đã nhận";
		                    			else if(tt.equals("3"))
		                    			{
			                    			trangthai = "Hoàn tất";
		                    			}
		                    			else if(tt.equals("4"))
		                    			{
		                    				trangthai = "Không nhận hàng";
	                    				}
		                    		} 
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<%-- <TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD> --%>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("1")){ %>
		                    
		                    	<%if(quyen[Utility.SUA]!=0) {%>
                                	<A href = "../../ErpNhapkhotraveUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                
                                <%if(quyen[Utility.CHOT]!=0) {%>
                                
                               		<A href = "../../ErpNhapkhotraveSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt nhập kho này?')) return false;"></A>&nbsp;
                                <%} %>
                                
                                <%if(quyen[Utility.XOA]!=0) {%>
                              	<A href = "../../ErpNhapkhotraveSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/Delete20.png" alt="Không nhận hàng" title="Không nhận hàng" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa nhập kho này?')) return false;"></A>
                              	<%} %>
                              										
		                    <%} else{ %>
		                    	
		                    	<%if(quyen[Utility.XEM]!=0) {%>
		                    		<A href = "../../ErpNhapkhotraveUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A> &nbsp;
		                    	<%} %>
		                    	
		                    	<%if(quyen[Utility.CHOT]!=0 && tt.equals("2") ) {%>
		                    		<A href = "../../ErpNhapkhotraveSvl?userId=<%=userId%>&unChot=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/unChot.png" alt="Mở Chốt" title="Mở Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn mở chốt nhập kho này?')) return false;"></A>
		                    	<%} %>
		                    	
		                    <% } %>
		                    </TD>
		                </TR>
                     <% m++; } nhapkhoRs.close(); } %>
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
													<img alt="Trang Truoc" src="../images/prev.gif" > &nbsp;
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
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" > &nbsp;
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
</html><%}%>