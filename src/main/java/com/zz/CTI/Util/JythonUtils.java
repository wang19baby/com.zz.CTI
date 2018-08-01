package com.zz.CTI.Util;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.poi.ss.formula.functions.T;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.springframework.util.ResourceUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @Title:JythonUtils
 * @Description:TODO(jython 工具类)
 * @Company: 
 * @author zhou.zhang
 * @date 2018年7月4日 下午5:14:07
 */
@SuppressWarnings("unchecked")
public class JythonUtils {

	/**
	 * 
	 * @Title:jythonInit
	 * @Description: TODO(初始化jython)
	 * @return
	 */
	public static PythonInterpreter jythonInit() {
		// 初始化site 配置
		Properties props = new Properties();
		props.put("python.home", "D:/Python/jython2.5.3"); // python Lib 或 jython Lib,根据系统中该文件目录路径
		props.put("python.console.encoding", "UTF-8");
		props.put("python.security.respectJavaAccessibility", "false");
		props.put("python.import.site", "false");
		Properties preprops = System.getProperties();
		PythonInterpreter.initialize(preprops, props, new String[0]);
		// 创建PythonInterpreter 对象
		PythonInterpreter interp = new PythonInterpreter();
		PySystemState sys = Py.getSystemState();   
		sys.path.add("D:/Python/SDK/Python35-64/Lib/site-packages");
		return interp;
	}

	/**
	 * 
	 * @Title:loadPythonFile
	 * @Description: TODO(加载python 源码文件)
	 * @param interp
	 * @param filePath 比如：F:\\jpython_jar\\jpythonTest\\pythonTest.py
	 * @return
	 */
	public static PythonInterpreter loadPythonFile(PythonInterpreter interp, String filePath) {
		interp.execfile(filePath);
		return interp;
	}

	/**
	 * 
	 * @Title:loadPythonFunc
	 * @Description: TODO(加载python源码文件中的某个方法)
	 * @param interp
	 * @param functionName
	 * @return
	 */
	public static PyFunction loadPythonFunc(PythonInterpreter interp, String functionName) {

		// 加载方法
		PyFunction func = interp.get(functionName, PyFunction.class);
		return func;
	}

	/**
	 * 
	 * @Title:execFunc
	 * @Description: TODO(执行无参方法,返回PyObject)
	 * @param func
	 * @return
	 */
	public static PyObject execFunc(PyFunction func) {
		PyObject pyobj = func.__call__();
		return pyobj;
	}

	/**
	 * 
	 * @Title:execFuncToString
	 * @Description: TODO(执行无参方法,返回一个字符串)
	 * @param func
	 * @return
	 */
	public static String execFuncToString(PyFunction func) {
		PyObject pyobj = execFunc(func);
		return (String) pyobj.__tojava__(String.class);
	}

	/**
	 * 
	 * @Title:execFuncToString2
	 * @Description: TODO(执行有参方法,返回一个字符串)
	 * @param func
	 * @param paramName
	 * @return
	 */
	public static String execFuncToString2(PyFunction func, String paramName) {
		PyObject pyobj = func.__call__(new PyString(paramName));
		return (String) pyobj.__tojava__(String.class);
	}
	
	/**
	 * 
	 * @Title:execFuncToString2
	 * @Description: TODO(执行有参方法,返回json)
	 * @param func
	 * @param paramName
	 * @return
	 */
	public static JSONObject execFuncToString2(PyFunction func, JSONObject paramName) {
		PyObject pyobj = func.__call__(new PyString(Encodes.encodeBase64(paramName.toString())));
		return JSONObject.fromObject(pyobj.toString());
	}

	/**
	 * 
	 * @Title:execFuncToInteger
	 * @Description: TODO(执行无参方法,返回一个Integer)
	 * @param func
	 * @return
	 */
	public Integer execFuncToInteger(PyFunction func) {
		PyObject pyobj = execFunc(func);
		return (Integer) pyobj.__tojava__(Integer.class);
	}

	/**
	 * 
	 * @Title:execFuncToList
	 * @Description: TODO(执行无参方法,返回一个List)
	 * @param func
	 * @return
	 */
	public List<T> execFuncToList(PyFunction func) {
		PyObject pyobj = execFunc(func);
		return (List<T>) pyobj.__tojava__(List.class);
	}

	/**
	 * 
	 * @Title:execFuncToMap
	 * @Description: TODO(执行无参方法,返回一个Map<String, Object>)
	 * @param func
	 * @return
	 */
	public Map<String, Object> execFuncToMap(PyFunction func) {
		PyObject pyobj = execFunc(func);
		return (Map<String, Object>) pyobj.__tojava__(Map.class);
	}

	public static void main(String[] args) throws FileNotFoundException {
		PythonInterpreter interp = jythonInit();
//		// 文件名
		String filePath = ResourceUtils.getFile("classpath:Script/test.py").getPath();
		interp = loadPythonFile(interp, filePath);
//		// 函数名
		String functionName = "test";
		PyFunction func = loadPythonFunc(interp, functionName);
		
//		// 执行无参方法，返回PyObject
//		PyObject pyobj = execFunc(func);
//		// 执行无参方法，返回String
//		String resultStr = execFuncToString(func);
		
		// 执行有参方法，返回String
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("zz", "撒啊是否");
		jsonObject.put("ww", 22);
		System.out.println("结果：" + execFuncToString2(func, jsonObject));
	}
}
