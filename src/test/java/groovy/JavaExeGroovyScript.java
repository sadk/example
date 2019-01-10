package groovy;  
  
import groovy.lang.Binding;  
import groovy.lang.GroovyShell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;  
import javax.script.ScriptException;  
  
public class JavaExeGroovyScript {  
  
    public Object doit() {  
        ScriptEngineManager factory = new ScriptEngineManager(JavaExeGroovyScript.class.getClassLoader());  
        ScriptEngine scriptEngine =  factory.getEngineByName("Groovy");//或者"Groovy" groovy版本要1.6以上的  
        try {  
            scriptEngine.put("test", "hello world!");  
            scriptEngine.put("outer", this);  
            scriptEngine.eval("println test; outer.java_out()");  
        } catch (ScriptException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
          
        Binding bb = new Binding();  
        bb.setVariable("test", "hello world!");  
        bb.setProperty("outer", this);  
        GroovyShell gs = new GroovyShell(bb);  
          
          
        return gs.evaluate("println test; outer.java_out()");  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
    	  String type = "List<String>";
          String jsonString = "[\"wei.hu\",\"mengna.shi\",\"fastJson\"]";

          Binding binding = new Binding();
          binding.setProperty("jsonString", jsonString);
          binding.setProperty("type", type);
          GroovyShell groovyShell = new GroovyShell(binding);
          String code =   "import com.alibaba.fastjson.JSON;\n" +
                  "import com.alibaba.fastjson.TypeReference;\n" +
                  "TypeReference<"+ type +"> typeReference = new TypeReference<" + type +">(){};\n" +
                  "JSON.parseObject(jsonString, typeReference);";
          
          System.out.println(code);
          Object s= groovyShell.evaluate(code);
 
          System.out.println(s);
          
		if (true) return;
    	
         
         
    	JavaExeGroovyScript te = new JavaExeGroovyScript();  
        te.doit();  
          
    }  
      
    public void java_out(){  
        System.out.println("out from java");  
    }  
  
}  
