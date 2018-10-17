

$("<style type='text/css'> #div_loading > img { float:left; z-index:200000001} " +
		"    #div_loading {width:300px; position: absolute;margin-left: " +
		"    auto;top:100px;margin-right: auto;left: 0;right: 0;} " +
		"    #bg_loading{position: fixed;\r\n" + 
		"    left: 0px;\r\n" + 
		"    top: 0px;\r\n" + 
		"    width: 100%;\r\n" + 
		"    height: 500%; " +
		"    background: rgba(255,255,255,0.9); " +
		"    z-index: 100000001;\r\n" + 
		"    min-height : 1200px} " +
		"	#loading1{" +
		"	margin-top: 200px; "+
		"   text-align: center; "+
		"   font-size: 18px;" +
		"}" +
		"</style>").appendTo("head");

var div = $('<div>').attr({
	id : 'bg_loading',
	width:'100%',
	height:'500'
}).appendTo('body');

var p1 = $('<div>').attr({
	id : 'div_loading',
	width:'300',
	height:'100%'
}).appendTo(div);
//var img = $('<img>').attr({
//    id: 'loading1',
//    src : "../images/dangtai.png",
//    width : '387',
//    height : '177'
//    
//}).appendTo(p1);

var p1 = $('<p>').attr({
	id : 'loading1'
});
p1.text("Đang tải dữ liệu ...");
p1.appendTo(div);

//var img1 = $('<img>').attr({
//    id: 'Newtoyo_Lego',
//    //src : '../images/loading02.gif',
//    width : '200',
//    height : '200'
//    
//}).appendTo(p1);

div.hide();

var form = $('form');



/*if (!$.browser.mozilla)*/
{
	
	setTimeout(function(){ 
		console.log("cookie " + document.cookie); 
		
	}, 3000);
	
	window.unload = function() {
		console.log("unloading");
	}
	window.onload = function() {
		if (window.jQuery) { // Kiểm tra xem jsp đã có thư viện JQuery chưa? Có rồi mới chạy
			// jQuery is loaded 
			console.log("Jquery load thành công!");
			
			//BEGIN chặn thao tác khi trang load mới (Sau khi chọn chức năng trên leftmenu)
			//startload();
			
			console.log("cookie " + document.cookie); 
			
			
			$(window).load(function() {
				endload();
			});
	
			$(document).ready(function() {
				endload();
				$(window).load(function() { 
				    console.log("xong r");
				  });
			});
			
			
			//END chặn thao tác khi trang mới load
			$(window).resize(function(){
				endload();
				console.log("xong r ne");
		    });
	
			//BEGIN chặn thao tác khi trang chuyển URL (Save, hiển thị, chốt, xóa, tìm kiếm, đăng xuất, đổi mật khẩu trừ các nút bị e.prevenDefault. Vd: Thêm dòng, popuphóa đơn)
			window.onbeforeunload = function(event) {
				startload();
				console.log("onbeforeunload");
				//$("form").before('<div class="loader222"  id="divTIMKIEM"></div>');
			}
			
			
			document.onreadystatechange = function(){
				console.log("onreaddy");
				  if(document.readyState === 'complete'){
					  console.log("load complete");
				  }
			}
			$(window).on('pageshow',function(){
				console.log("load pageshow");
			});
			/*$("a").live('click', function(){
				$("form").before('<div class="loader222"  id="divTIMKIEM"></div>');
				
			});*/
			//END chặn thao tác khi trang chuyển URL
	
			function startload() {
				//debugger;
				//location.reload();
				console.log('startload');
				//$("form").before('<div class="loader222"  id="divTIMKIEM"></div>');
				//img1.show();
				div.show();
				//form.hide();
			}
	
			function endload() {
				//debugger;
				console.log('endload');
				//$("#divTIMKIEM").remove();
				//img1.hide();
				div.hide();
				//form.show();
			}
		} else {
			// jQuery is not loaded
			console.log("Xin thêm file Jquery để thực hiện hoặc phát triển thêm bằng mã JS");
	
		}
	}
}

