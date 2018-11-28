##################################################
######										######
######     截止当前使用版本是：开发版，1.4.3.2   	######
######										######
##################################################

百度的uditor富文本编辑器，安装需要如下jar包：
commons-codec-1.9.jar
commons-fileupload-1.3.1.jar
commons-io-2.4.jar
json.jar
ueditor-1.1.2.jar

(1)ueditor-1.1.2.jar是百度专有的包，用如下命令安装到本地库
mvn install:install-file -DgroupId=com.baidu -DartifactId=ueditor -Dversion=1.1.2 -Dpackaging=jar -Dfile=ueditor-1.1.2.jar

(2)json.jar经测试采用版本,如下：
	<dependency>
	    <groupId>org.json</groupId>
	    <artifactId>json</artifactId>
	    <version>20151123</version>
	</dependency>