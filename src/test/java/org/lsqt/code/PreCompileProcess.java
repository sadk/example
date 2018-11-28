package org.lsqt.code;

public class PreCompileProcess { 

    public static void main(String[] args) { 

        // 为 Student 添加字段 
        AddField add = new AddField(Student.class); 

        // 添加一个名为 address，类型为 java.lang.String 的 public 字段 
        add.addPublicField("address", "Ljava/lang/String;"); 

        // 再增加一个名为 tel，类型为 int 的 public 方法 
        add.addPublicField("tel", "I"); 

        // 重新生成 .class 文件 
        add.writeByteCode(); 
    } 
} 