<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.tieuchuankiemnghiem.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpTieuChuanKiemNghiemList obj = (IErpTieuChuanKiemNghiemList)session.getAttribute("obj"); %>
<%-- <% ResultSet lsxList = (ResultSet)obj.getDataRs(); %> --%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% ResultSet tieuchuanRs = obj.getTieuchuanRs(); %>

<%
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("ErpTieuChuanKiemNghiemSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpTieuChuanKiemNghiemSvl", "" , userId);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE><%=getServletContext().getInitParameter("TITLENAME") %></TITLE>
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
	 
	 function clearform()
	 {   
	    
	    document.forms["erpLsxForm"].tungaySX.value = "";
	    document.forms["erpLsxForm"].denngaySX.value = "";
	   
	    document.forms["erpLsxForm"].masanpham.value = "";
	    document.forms["erpLsxForm"].matieuchuan.value = "";

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
		/* $(document).ready(function() { $("#nguoitao,#nguoisua,#khonhanId,#trangthai,#khochuyenId").select2(); }); */
     $(document).ready(function() { $("select").select2();  });
 </script>
 
 
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

</head>
<body>
<form name="erpLsxForm" method="post" action="../../ErpTieuChuanKiemNghiemSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<%-- <input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" > --%>
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="id" value='<%= obj.getId()%>'>
<input type="hidden" name="chungtu" id="chungtu"  >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Dữ liệu nền > Hồ sơ kiểm nghiệm > Tiêu chuẩn kiểm nghiệm
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
                        <TD class="plainlabel" width="100px" >Từ ngày </TD>
                        <TD class="plainlabel" width="250px">
                            <input type="text" class="days" onchange="submitform()"
                                   id="tungaySX" name="tungaySX" value="<%= obj.getTungay() %>" maxlength="10"  />
                        </TD>
                        
                        <TD class="plainlabel" width="100px" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" onchange="submitform()"
                                   id="denngaySX" name="denngaySX" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" width="100px" >Mã tiêu chuẩn </TD>
                        <TD class="plainlabel" width="250px">
                            <input type="text" onchange="submitform()"
                                   id="matieuchuan" name="matieuchuan" value="<%= obj.getMaTieuChuan() %>"  />
                        </TD>
                        
                        <TD class="plainlabel" width="100px" >Mả sản phẩm </TD>
                        <TD class="plainlabel" >
                            <input type="text" onchange="submitform()"
                                   id="masanpham" name="masanpham" value="<%= obj.getMaSanPham() %>"  />
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
            	<legend><span class="legendtitle">Tiêu chuẩn kiểm nghiệm </span>&nbsp;&nbsp;
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="5%">Số phiếu</TH>
	                    <TH align="center" width="8%">Ngày chuyển</TH>
	                    <TH align="center" width="7%">Mã tiêu chuẩn</TH>
	                    <TH align="center" width="7%">Mã sản phẩm</TH>  
	                 	<TH align="center" width="20%">Sản phẩm</TH>  
	                 	<TH align="center" width="10%">Trạng thái</TH>
	                    <TH align="center" width="10%">Ngày sửa</TH>
	                    <TH align="center" width="10%">Người sửa</TH>
	                    <TH align="center" width="10%" >Tác vụ</TH>
	                </TR>
					
					
						<% int m=0; if(tieuchuanRs != null) {
							while(tieuchuanRs.next()) {
						%>
						
						<% 
	                    if((m % 2 ) == 0) { %>
		                         	<TR  class='tbdarkrow'     >
		                        <%}else{ %>
		                          	<TR  class='tblightrow' >
		                        <%} %>
						
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("TCID") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("ngaycap") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("TCMA") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("SPMA") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("SPTEN") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("tctrangthai") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("ngaysuatc") %></TD>  
							<TD style=" text-align: center;"><%= tieuchuanRs.getString("NVSUATEN") %></TD>  
							
							<TD align="center"> 
		                    
		                    <%if(quyen[Utility.SUA]!=0){ %>
                                	<A href = "../../ErpTieuChuanKiemNghiemUpdateSvl?userId=<%=userId%>&update=<%=tieuchuanRs.getString("TCID") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                               <% } %>
                                
                                
                                <%if(quyen[Utility.XOA]!=0){ %>
                                
                                	<A href = "../../ErpTieuChuanKiemNghiemSvl?userId=<%=userId%>&delete=<%= tieuchuanRs.getString("TCID") %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn đặt hàng này?')) return false;"></A>
                              	<% } %>	
                              	
                              									
		                   
		                    </TD>
						</tr>	
						
								
					<%	m++;	}
							
						} %>
													<TR class="tbfooter">
														<TD align="center" colspan="15">&nbsp;</TD>
													</TR>
					
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
<%-- <% 
try{
	if(lsxList!=null){
		lsxList.close();
	}
	
	obj.DBclose();
	session.setAttribute("obj",null);
}catch(Exception er){
	
}
 %> --%>
</body>
</html>
<% } %>