<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查看认证资料</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pagertree.js" ></script>
    <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
 	</style>
</head>
<body>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div  title="员工简要信息、OCR与人脸识别影像文件" region="north" height="100" showSplitIcon="false" >
		        <div class="mini-fit" >
					<form id="edit-form1" method="post" style="height:100%; overflow:auto;">
					            <div style="padding:4px;">
							        <table style="width:100%;" border="0">
										<tr>
											<td style="width:60px;">姓名</td>
											<td style="width:90px">
												<input id="userName" name="userName"  class="mini-textbox" allowInput="false" />
												<input name="userIds" id="userIds" name="userIds" class="mini-hidden"/>
											</td>
											<td style="width:60px;">手机</td>
											<td style="width:90px">
												<input id="phone" name="phone"  class="mini-textbox"   allowInput="false"  />
											</td>
											<td style="width:100px;">性别</td>
											<td style="width:120px">
												<input name="sex" id="sex"  class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=sex" readonly="readonly"/>
											</td>
											<td style="width:90px;">身份证</td>
											<td style="width:200px">
												<input id="idCard" name="idCard"  class="mini-textbox"   readonly="readonly"   />
											</td>
										</tr>
										<tr>
											<td style="width:60px;">入职公司</td>
											<td style="width:150px">
												<input name="companyName" id="companyName" class="mini-textbox" onclick = "onClickCompanyName()" emptyText="请选择入职企业" readonly="readonly"/>
								 				<input name="companyCode" id="companyCode" class="mini-hidden"/>
											</td>
											<td style="width:60px;">入职日期</td>
											<td style="width:150px">
												<input id="entryTime" name="entryTime" class="mini-textbox" emptyText="请选择入职日期"  readonly="readonly"/>
											</td>
											<td>入职状态</td>
											<td>
												<input id="entryStatus" name="entryStatus" required="true" readonly="readonly" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status_new" />
											</td>
											<td>所属门店</td>
											<td>
												<input id="storeName" name="storeName"  class="mini-textbox"  readonly="readonly"/>
											</td>
											
							        </table>
							    </div>
					</form>
		        </div>
    </div>
    
	 <div title="south" region="south" showSplit="false" showHeader="false" height="38" showSplitIcon="false" > 
			<div id="subbtn" style="text-align:center;padding:4px;"> 
				<a class="mini-button" iconCls="icon-ok" onclick="onOk('yes')" style="width:100px;margin-right:20px;">审核通过</a>
				<a class="mini-button" iconCls="icon-cancel" onclick="onOk('no')" style="width:100px;margin-right:20px;">审核不通过</a>
				<a class="mini-button" iconCls="icon-no" onclick="onCancel" style="width:100px;">关闭窗口</a> 
			</div>
    </div>  
 
    <div title="影像资源 " showProxyText="true" region="west" width="220" expanded="true" showSplitIcon="true" >
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" style="width:100%;" 
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true">        
            </ul>
        </div>
    </div>
    <!-- 
    <div title="east" region="east"  showCloseButton="true" showSplitIcon="true" showSplitIcon="true">
        east
    </div>
     -->
    <div title="center" region="center"  >
				<div class="mini-splitter" style="width:100%;height:100%;">
				    <div size="75%" showCollapseButton="true">
						
				        <div class="mini-toolbar" style="padding:0px;border-top:0;border-left:0;border-right:0;">                
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
									
									<td style="white-space:nowrap;">
				                        <span id="uploadTimeSpan"></span>
				                    </td>
				                     
								</tr>
							</table>
				        </div>
				        <div align="center" style="width: 100%;height: 100%; overflow-y:scroll; overflow-x:scroll;"> <!-- overflow-y:scroll; overflow-x:scroll; -->
				        	<div id="imgInfo" style="margin: auto;"></div>
				        </div>
				    </div>
				    <div showCollapseButton="true">
				        <div class="mini-toolbar" style="padding:0px;border-top:0;border-left:0;border-right:0;">                
							<table style="width:100%;">
								<tr>
									<td style="width:100%;" align="center">
										<a class="mini-button"  plain="true"  iconCls="icon-search" onclick="">文件缩略图</a>
									</td>
								</tr>
							</table>
				        </div>
				        <div style="width:100%;height:100%;overflow-y:scroll;overflow-x:scroll;">
				        	<div id="imgInfo2" style="margin-top: 0;text-align: center;"></div>
				        </div>
				    </div>        
				</div>
		    
		    
		</div>
       
    </div>
</div>

    

</body>
    <script type="text/javascript">
        mini.parse();
        var layout = mini.get("layout1");
        var form = new mini.Form("edit-form1");
        var tree = mini.get("tree1");

        
		function SaveData(e) {
			var o = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
			var msg = "";
			o.entryTime = mini.get("entryTime").text;
			if (e == 'yes') {
				o.entryStatus = 400;
				msg = "审核通过，用户将直接入职，是否确认？";
			}
			if (e =='no') {
				o.entryStatus = 500;
				msg = "审核不通过，用户将入职失败，是否确认？";
			}
			
	        mini.confirm(msg, "确定？",
	                function (action) {
	                    if (action == "ok") {
	                    	submitForm();
	                    }  
	                }
	            );
	        
	        var submitForm = function () {
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/user_entry_info/batch_short_update",
					dataType: 'json', data: o,type : 'post',
					success : function(text) {
						CloseWindow("save");
					}
				});
	        }
		}
		
      	function onClickCompanyName() {
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/company/selector_company.jsp?multiSelect=false",
				title : "企业选择",
				width : 550,
				height : 480,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					if(data) {
						data = mini.clone(data);
						mini.get("companyCode").setValue(data.code);
						mini.get("companyName").setValue(data.fullName);
						
					}
				}
			});	
      	}
        
        function downloadImg() {
        	window.location = "${pageContext.request.contextPath}/rst/user_entry_info/download_img?url="+$("#imgObj").attr("src");
        }
        
        tree.on("nodeclick", function (e) {
        	var node = e.node;
        	//大图
       		$("#imgInfo").empty();
       		$("#imgInfo").append("<img id='imgObj' onclick='openOriginal(\""+node.url+"\")' src='"+node.url+"' />");
       	 
       		//缩略图 
   			$("#imgInfo2").empty();
   			$("#imgInfo2").append("<img id='imgObj2' src='"+node.url+"'  onclick='openOriginal(\""+node.url+"\")'  onload='drawImage(this,350,230)' />");

   			
        	/* 
        	if(node.type == "2" || node.type == "3" ) {
        		window.open(node.url,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=800, height=600");
        	}
        	 */
        	
        	
        	/*
        	var uploadTimeText = mini.formatDate (node.uploadTime, "yyyy-MM-dd HH:mm:ss");
        	$("#uploadTimeSpan").html("上传时间: "+uploadTimeText);
        	*/
        	
        	resetTurn();
        });
        
        function showBigImg(url,uploadTimeStr) { // 缩略图显示成大图
        	$("#imgInfo").empty();
    		$("#imgInfo").append("<img id='imgObj' onclick='openOriginal(\""+url+"\")' src='"+url+"' />");
        	$("#uploadTimeSpan").html("上传时间: "+uploadTimeStr);
        }
        
        function openOriginal(url) {//全屏打开原图
        	var url= "${pageContext.request.contextPath}/apps/default/admin/rst/user_entry_info/img_show.jsp?url="+url ;
        	window.open(url,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
        }
          
        
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
        
        function resetTurn() {
        	var img = document.getElementById('imgInfo');
        	current = 0;
        	img.style.transform = 'rotate(0deg)';
        }
        
        var rate = 0.1;
        var cnt=1;
        function zoomOut() { //放大
        	var image = new Image();
        	var img = document.getElementById('imgObj');
        	image.src = img.src;
        	
        	img.width = image.width*(1+rate*(cnt++));
        	img.height= image.height*(1+rate*(cnt++));
        	//drawImage(img,);
        	//alert(image.width);
        }
        
        function zoomIn() { //缩小
        	var image = new Image();
        	var img = document.getElementById('imgObj');
        	image.src = img.src;
        	
        	img.width = image.width*(1+rate*(cnt--));
        	img.height= image.height*(1+rate*(cnt--));
        	 
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

		
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			tree.setUrl("${pageContext.request.contextPath}/rst/user_entry_info/tree?userCode="+data.userCode);
			data.userIds = data.userCode;
			form.setData(data)
		}

		function GetData() {
			var o = form.getData();
			return o;
		}
		
		function CloseWindow(action) {
			if(action == "close" && form.isChanged()) {
				if(confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if(window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		function onOk(e) {
			SaveData(e);
		}

		function onCancel(e) {
			CloseWindow("cancel");
		}
		
    </script>
</html>