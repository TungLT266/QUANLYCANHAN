<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.cttrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% ICttrungbayList obj = (ICttrungbayList)session.getAttribute("obj"); %>
<% List<ICttrungbay> cttbList = (List<ICttrungbay>)obj.getCttbList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{
	int[] quyen = new  int[6];
	quyen = util.Getquyen("CttrungbaySvl","",userId);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
     <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
     <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
  	</style>
  
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
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
		    document.forms["cttbForm"].submit();
		 }
		 function newform()
		 {   
			document.forms["cttbForm"].action.value = "Tao moi";
		    document.forms["cttbForm"].submit();
		 }
		 function clearform()
		 {   
		    document.forms["cttbForm"].diengiai.value = "";
		    document.forms["cttbForm"].tungay.value = "";
		    document.forms["cttbForm"].denngay.value = "";
		    document.forms["cttbForm"].submit();
		 }
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cttbForm" method="post" action="../../CttrungbaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý trưng bày > Khai báo > Chương trình trưng bày
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
   	
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle">Tiêu chí tìm kiếm</legend>
        <div style="width:100%; float:none" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">								
                    <TR>
                        <TD width="10%" class="plainlabel">Mã CTTB </TD>
                        <TD class="plainlabel"><input type="text" value="<%= obj.getDiengiai() %>" name="diengiai" size="40"></TD>
                    </TR>               
                    <TR>
                        <TD class="plainlabel" >Ngày trưng bày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"
                                        id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Ngày kết thúc </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"
                                        id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <tr style="background-color:#C5E8CD">
                        <td colspan="2">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    <a class="button" href="javascript:submitform()">
                              <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>
                        </td>
                    </tr>      				
                </TABLE>       
         </div>
      </fieldset>  
    </div>
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend><span class="legendtitle"> Chương trình trưng bày </span>&nbsp;&nbsp;&nbsp;
    	<%if(quyen[Utility.THEM]!=0){ %>
        	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                    <%} %>
        </legend>
        <div style="width:100%; float:none" align="left">
            <TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
                <TR class="tbheader">
                    <TH align="center">Mã CTTB</TH>
                    <TH align="center"> Diễn giải</TH>
                    <TH align="center" onMouseover="ddrivetip('Ngay bat dau tinh doanh so', 250)"; onMouseout="hideddrivetip()">Ngày TDS</TH>
                    <TH align="center" onMouseover="ddrivetip('Ngay ket thuc tinh doanh so', 250)"; onMouseout="hideddrivetip()">Ngày KT TDS</TH>				
                    <TH align="center" onMouseover="ddrivetip('Ngay bat dau trung bay', 250)"; onMouseout="hideddrivetip()">Ngày TB</TH>
                    <TH align="center" onMouseover="ddrivetip('Ngay ket thuc trung bay', 250)"; onMouseout="hideddrivetip()">Ngày KT TB</TH>
                    <TH align="center" onMouseover="ddrivetip('Số lần thanh toán', 250)"; onMouseout="hideddrivetip()">SL CT</TH>
                    <TH align="center">Ngày tạo </TH>
                    <TH align="center">Người tạo</TH>
                    <TH align="center">Ngày sửa</TH>
                    <TH align="center">Người sửa</TH>
                    <TH align="center">Tác vụ</TH>
                </TR>
                <%      
                    ICttrungbay cttb = null;
                    int size = cttbList.size();
                    int m = 0;
                    while (m < size){
                        cttb = cttbList.get(m);
                        if(m%2==0){
                %> 
                  <TR class='tbdarkrow'>
                  <% 
                        }else{
                        	%>
                      <TR class='tblightrow'>   	
                       <% }
	                  	String diengiai = "";
	                  	if(cttb.getDiengiai().length() > 0) 
	                	  	diengiai = cttb.getScheme() + " -- " + cttb.getDiengiai();
	                  	else
	                  		diengiai = cttb.getScheme(); %>
                  <TD align="center" onMouseover="ddrivetip('<%= diengiai %>', 250)"; onMouseout="hideddrivetip()"><%= cttb.getScheme() %></TD>
                  <TD align="right"><%= cttb.getNgansach() %></TD>
                  <TD align="center"><%= cttb.getNgayTds() %></TD>
                  <TD align="center"><%= cttb.getNgayktTds() %></TD>
                  <TD align="center"><%= cttb.getNgayTb() %></TD>
                  <TD align="center"><%= cttb.getNgayktTb() %></TD>							                                    
                  <TD align="right"><%= cttb.getSolantt() %></TD>
                  <TD align="left"><%= cttb.getNgaytao() %></TD>
                  <TD align="left"><%= cttb.getNguoitao() %></TD>
                  <TD align="left"><%= cttb.getNgaysua() %></TD>	
                  <TD align="left"><%= cttb.getNguoisua() %></TD>		
                  <TD align="center">   
                  <%if(quyen[Utility.SUA]!=0){ %>                  
                      <A href = "../../CttrungbayUpdateSvl?userId=<%=userId%>&update=<%= cttb.getId()%>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border="0"></A>&nbsp;
                      <%} %>
                      <%if(quyen[Utility.XOA]!=0){ %>
                      <A href = "../../CttrungbaySvl?userId=<%=userId%>&delete=<%= cttb.getId() %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa chương trình trưng bày?')) return false;"></A>                                         								
                 <%} %>
                  </TD>
              	</TR>
                 <% m++; }%>
                
                <TR>
                    <TD align="center" colspan="15" class="tbfooter">
                    |<   <   1 to 20 of 100   >   >| </TD>
                </TR>
            </TABLE>						
         </div>
      </fieldset>
  </div> 
</div>

</form>
</BODY>
</html>
<%}%>