package org.lsqt.code;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 可以嵌入式使用，例如工作流，角本,见groovy角本
 * @author yuanmm
 *
 */
public class NashornTest {

	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		System.out.println(engine.getClass().getName());
		System.out.println("Result:" + engine.eval("function f() { return 1; }; f() + 1;"));
	}

}
