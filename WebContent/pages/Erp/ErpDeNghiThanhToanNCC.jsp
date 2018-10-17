<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.uynhiemchi.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% IErpDenghithanhtoanNCCList obj = (IErpDenghithanhtoanNCCList)session.getAttribute("obj"); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>
<% ResultSet htttList = (ResultSet)obj.getHtttList(); %>
<% ResultSet tthdList = (ResultSet)obj.getTThoadonList(); %>
<% ResultSet nvList = (ResultSet)obj.getNvList(); %>
<% ResultSet loaihoadonList = (ResultSet)obj.getLoaihoadonList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%  
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/SalesUp/index.jsp");
}else{	

	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpDenghithanhtoanNCCSvl","123",userId);


obj.setNextSplittings(); %>

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
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	
	
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
		document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].trangthai.value = "";
	    document.forms["erpDmhForm"].sohoadon.value = "";
	    document.forms["erpDmhForm"].sochungtu.value = "";
	    document.forms["erpDmhForm"].loaihoadon.value = "";
	    document.forms["erpDmhForm"].nvId.value = "";
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
	</SCRIPT>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
	
	
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpDenghithanhtoanNCCSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải trả > Đề nghị thanh toán NCC
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
                        <TD class="plainlabel"  width="30%">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" width="15%">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     
                    <TR>
                        <TD class="plainlabel" >Số hóa đơn NCC</TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sohoadon" name="sohoadon" value="<%= obj.getSohoadon() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                         <TD class="plainlabel" >Số chứng từ </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sochungtu" name="sochungtu" value="<%= obj.getSochungtu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                   <TR>
                        <%-- <TD class="plainlabel" valign="middle" >Hình thức thanh toán </TD>
                        <TD class="plainlabel" valign="middle">
                            <select id= "httt" name="httt" onchange="submitform()"  style="width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(htttList != null)
	                        		{
	                        			while(htttList.next())
	                        			{  
	                        			if( htttList.getString("pk_seq").equals(obj.getHtttId())){ %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" selected="selected" ><%= htttList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" ><%= htttList.getString("ten") %></option>
	                        		 <% } } htttList.close();
	                        		}
	                        	%>
                            </select>
                        </TD> --%>   
                                          
                    </TR> 
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Trạng thái </TD> 
                        <TD class="plainlabel" valign="middle">
							<select name = "trangthai" id= "trangthai" style = "width:200px" onchange="submitform()">
								<%if(obj.getTrangthai().equals("0")){ %>
								<option value=""></option>
								<option value="0" selected="selected">Chưa chốt</option>
								<option value="1">Đã chốt</option>
								<option value="2">Đã xóa</option>
								<%}else if(obj.getTrangthai().equals("1")){ %>
								<option value=""></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" selected="selected">Đã chốt</option>
								<option value="2">Đã xóa</option>
								<%} else if(obj.getTrangthai().equals("2")){ %>
								<option value=""></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" >Đã chốt</option>
								<option value="2" selected="selected">Đã xóa</option>
								<%} else if(obj.getTrangthai().equals("3")){ %>
								<option value=""></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" >Đã chốt</option>
								<option value="2" >Đã xóa</option>
								<%} else { %>
								<option value="" selected="selected"></option>
								<option value="0" >Chưa chốt</option>
								<option value="1" >Đã chốt</option>
								<option value="2" >Đã xóa</option>
								<%} %>
							</select>
                        </TD>  
                        
                        <TD class="plainlabel" valign="middle" >Nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select id = "nccId" name="nccId" onchange="submitform()"  style="width:400px">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			if( nccList.getString("pk_seq").equals(obj.getNccId())){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("nccTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("nccTen") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                   
                   <TR>
                         
                        <TD class="plainlabel" valign="middle" >Loại hóa đơn </TD> 
                        <TD class="plainlabel" valign="middle">
							 <select id = "loaihoadon" name="loaihoadon" multiple="multiple" onchange="submitform()"  style="width:400px">
                            	<option value=""></option>
                            	<%
	                        		if(loaihoadonList != null)
	                        		{
	                        			while(loaihoadonList.next())
	                        			{  
	                        			if( obj.getLoaihoadon().contains(loaihoadonList.getString("pk_seq")) ){ %>
	                        				<option value="<%= loaihoadonList.getString("pk_seq") %>" selected="selected" ><%= loaihoadonList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= loaihoadonList.getString("pk_seq") %>" ><%= loaihoadonList.getString("ten") %></option>
	                        		 <% } } loaihoadonList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>  
                         <TD class="plainlabel" valign="middle" >Nhân viên </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select id = "nvId" name="nvId" onchange="submitform()"  style="width:400px">
                            	<option value=""></option>
                            	<%
	                        		if(nvList != null)
	                        		{
	                        			while(nvList.next())
	                        			{  
	                        			if( nvList.getString("pk_seq").equals(obj.getNvId())){ %>
	                        				<option value="<%= nvList.getString("pk_seq") %>" selected="selected" ><%= nvList.getString("nvTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nvList.getString("pk_seq") %>" ><%= nvList.getString("nvTen") %></option>
	                        		 <% } } nvList.close();
	                        		}
	                        	%>
                            </select>
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
            	<legend><span class="legendtitle"> Đề nghị thanh toán NCC </span>&nbsp;&nbsp;
            	<%//if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%//} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="6%">Số (Post No.)</TH>
	                    <TH align="center" width="6%">Ngày ghi nhận</TH>
	                    <TH align="center" width="6%">Số hóa đơn</TH>
	                    <TH align="center" width="28%">Nhà cung cấp </TH>
	                    <TH align="center" width="8%">Hình thức TT</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center" width="6%">Ngày tạo</TH>
	                    <TH align="left" >Người tạo </TH>
	                    <TH align="center" width="6%"> Ngày sửa </TH>
	                    <TH align="left" > Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%  int m = 0;
						for(int i =0; i < htList.size(); i ++)
						{ 
								IThongTinHienThi ht = htList.get(i);
									if((m % 2 ) == 0) {%>
		                         		<TR class="tbdarkrow">
			                     <%}else{ %>
			                          	<TR class="tblightrow">
			                        <%} %>
	
									<TD align="center"><%=  ht.getId() %></TD>
									
									<TD align="center"><%= ht.getNgayghinhan() %></TD>
									<TD align="center"><%= ht.getSohoadon() %></TD>
									<TD align="left"><%= ht.getKhachhang() %></TD>
									<TD align="left"><%= ht.getHttt() %></TD>
									<TD align="left">
										<%
											String trangthai = "";
											String tt = ht.getTrangthai();
											if(tt.equals("0"))
												trangthai = "Chưa chốt";
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
									</TD>
									<TD align="left"><%= ht.getNgaytao() %></TD>
									<TD align="left"><%= ht.getNguoitao()%></TD>
									<TD align="left"><%= ht.getNgaysua() %></TD>
									<TD align="left"><%= ht.getNguoisua() %></TD>
									<TD align="center"> 
				                    <% if(tt.equals("0")){ %>
				                    <%if(quyen[2]!=0){ %>
		                                <A href = "../../ErpDenghithanhtoanNCCUpdateSvl?userId=<%=userId%>&update=<%= ht.getId() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
		                                <A href = "../../ErpDenghithanhtoanNCCSvl?userId=<%=userId%>&chot=<%= ht.getId() %>"><img src="../images/Chot.png" alt="Chôt thanh toán" title="Chốt thanh toán" width="20" height="20" border=0 onclick="if(!confirm('Bạn có chắc chốt phiếu thanh toán này?')) return false;"></A>&nbsp; 
		                                <%} %>
		                                
		                               <%// Nếu có check Thanh toán từ tiền vay thì: khi đã nhập VAT & THUẾ thì mới cho chốt(VAT & THUẾ) %>
		                                <%if(quyen[4]!=0){ 		                                	
		                                  if( ht.getIsKTTDuyet().equals("1") && (Double.parseDouble(ht.getthanhtoantutienvay()) == 0  || (ht.getthanhtoantutienvay().equals("1") && Double.parseDouble(ht.getvat()) > 0 && Double.parseDouble(ht.getphinganhang()) > 0) ) ){%>		                              
		                                 <A id='chotphieu<%=ht.getId()%>'
							       			href=""><img
							       			src="../images/Chot.png" alt="Chốt thanh toán"
							       			width="20" height="20" title="Chốt thanh toán"
							      			border="0" onclick="if(!confirm('Bạn có chắc chốt phiếu thanh toán này?')) {return false ;}else{ processing('<%="chotphieu"+ht.getId()%>' , '../../ErpDenghithanhtoanNCCSvl?userId=<%=userId%>&chot=<%= ht.getId() %>');}"  >
										 </A>
										 <%}%>		                                  		                                  				                    	
		                                  
										  <% } %>
										
										  
		                                <%if(quyen[1]!=0){ %>
		                                <A href = "../../ErpDenghithanhtoanNCCSvl?userId=<%=userId%>&delete=<%= ht.getId() %>"><img src="../images/Delete20.png" alt="Xóa thanh toán" title="Xóa thanh toán" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu thanh toán này?')) return false;"></A>
		                                <% }%>									
				                    <%}else{ %>
				                    	<A href = "../../ErpDenghithanhtoanNCCUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
				                    	
				                    <%} %>
				                    </TD>
				                    </TR>
								<% m++;}%>
						 <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('erpDmhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('erpDmhForm', -1, 'view')"> &nbsp; <%} %>
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
</script>
<%
try{
	obj.DBclose();  
	session.setAttribute("obj",null);

}catch(Exception er){
	
}
}%>
</form>
</body>
</html>