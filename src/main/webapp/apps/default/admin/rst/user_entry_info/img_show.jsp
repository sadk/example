<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>影像查看</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
   	
    <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
 	</style>
</head>
<body >   
       <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
    <div class="mini-toolbar" style="padding:2px;border-bottom:0;">
        <table style="width:100%;">
            <tr>
            <td style="width:100%;">
				<a class="mini-button"  plain="true"  iconCls="icon-undo" onclick="leftTurn()">左转90度</a>
				<a class="mini-button"  plain="true"  iconCls="icon-redo" onclick="rightTurn()">右转90度</a>
				<a class="mini-button"  plain="true"  iconCls="icon-zoomin" onclick="zoomOut()">放大</a>
				<a class="mini-button"  plain="true"  iconCls="icon-zoomout" onclick="zoomIn()">缩小</a>
				<a class="mini-button"  plain="true"  iconCls="icon-reload" onclick="zoomReset()">最适大小</a>
				<a class="mini-button"  plain="true"  iconCls="icon-download" onclick="downloadImg()">下载</a>
            </td>
        </table>
    </div>
    <!--撑满页面-->
    <div class="mini-fit" >
    	<div id="imgInfo" style="margin: auto;padding: 0;vertical-align: middle;text-align: center;">
       		<img id="imgObj" src = "${param.url}" />
       	</div>
    </div>
    
    <script type="text/javascript">
        mini.parse();
        
        function downloadImg() {
        	window.location = "${pageContext.request.contextPath}/rst/user_entry_info/download_img?url=${param.url}";
        }
        
        
        document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==27){ // 按 Esc 
                window.close();
              }
            if(e && e.keyCode==113){ // 按 F2 
                 //要做的事情
               }            
             if(e && e.keyCode==13){ // enter 键
                 //要做的事情
            }
             if(e && e.keyCode==32){ // 空格 键
            	 window.close();
            }
        };
        
        // ------------------------------------------------------ 图片旋转、放大缩小处理 Begin:------------------------------------------------------------
        var current = 0;
        function rightTurn() {
        	var img = document.getElementById('imgInfo');
        	current = (current+90)%360;
        	img.style.transform = 'rotate('+current+'deg)';
        }
        
        function leftTurn() {
        	var img = document.getElementById('imgInfo');
        	current = (current-90)%360;
        	img.style.transform = 'rotate('+current+'deg)';
        }
        
        var rate = 0.1;
        var cnt=1;
        function zoomOut() { //放大
        	var image = new Image();
        	var img = document.getElementById('imgObj');
        	image.src = img.src;
        	
        	img.width = image.width*(1+rate*(cnt++));
        	img.height= image.height*(1+rate*(cnt++));;
        	//drawImage(img,);
        	//alert(image.width);
        }
        
        function zoomIn() { //缩小
        	var image = new Image();
        	var img = document.getElementById('imgObj');
        	image.src = img.src;
        	
        	img.width = image.width*(1+rate*(cnt--));
        	img.height= image.height*(1+rate*(cnt--));;
        	 
        }
        
        function zoomReset() {
        	rate = 0.1;
        	cnt=1;
        	
        	var image = new Image();
        	var img = document.getElementById('imgObj');
        	image.src = img.src;
        	
        	img.width = image.width ;
        	img.height= image.height ;
        }
        /*
        $(function(){//点击图片自转
            var current = 0;
            document.getElementById('imgInfo').onclick = function(){
                current = (current+90)%360;
                this.style.transform = 'rotate('+current+'deg)';
            }
        })
        */
 
        /**
		 * 图片按宽高比例进行自动缩放
		 * @param ImgObj
		 *     缩放图片源对象
		 * @param maxWidth
		 *     允许缩放的最大宽度
		 * @param maxHeight
		 *     允许缩放的最大高度
		 * @usage 
		 *     调用：<img src="图片" onload="javascript:DrawImage(this,100,100)">
		 */
		function drawImage(ImgObj, maxWidth, maxHeight){
		    var image = new Image();
		    //原图片原始地址（用于获取原图片的真实宽高，当<img>标签指定了宽、高时不受影响）
		    image.src = ImgObj.src;
		    // 用于设定图片的宽度和高度
		    var tempWidth;
		    var tempHeight;
		    
		    if(image.width > 0 && image.height > 0){
		        //原图片宽高比例 大于 指定的宽高比例，这就说明了原图片的宽度必然 > 高度
		        if (image.width/image.height >= maxWidth/maxHeight) {
		            if (image.width > maxWidth) {
		                tempWidth = maxWidth;
		                // 按原图片的比例进行缩放
		                tempHeight = (image.height * maxWidth) / image.width;
		            } else {
		                // 按原图片的大小进行缩放
		                tempWidth = image.width;
		                tempHeight = image.height;
		            }
		        } else {// 原图片的高度必然 > 宽度
		            if (image.height > maxHeight) {  
		                tempHeight = maxHeight;
		                // 按原图片的比例进行缩放
		                tempWidth = (image.width * maxHeight) / image.height;      
		            } else {
		                // 按原图片的大小进行缩放
		                tempWidth = image.width;
		                tempHeight = image.height;
		            }
		        }
		        // 设置页面图片的宽和高
		        ImgObj.height = tempHeight;
		        ImgObj.width = tempWidth;
		        // 提示图片的原来大小
		        ImgObj.alt = image.width + "×" + image.height;
		    }
		}
		// ------------------------------------------------------ 图片旋转、放大缩小处理  End!!!------------------------------------------------------------
		
    </script>

</body>
</html>