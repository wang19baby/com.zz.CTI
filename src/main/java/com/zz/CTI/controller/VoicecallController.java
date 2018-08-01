package com.zz.CTI.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.CTI.Bean.AgentCall;
import com.zz.CTI.Bean.User;
import com.zz.CTI.Shiro.ShiroUtil;
import com.zz.CTI.Util.DateUtil;
import com.zz.CTI.huawei.common.GlobalConstant;
import com.zz.CTI.huawei.service.CallService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;

/**
 * 语言通话
 * @author zhou.zhang
 *
 */
@Controller
@RequestMapping("/voicecall")
public class VoicecallController extends BaseController {
	@Resource
	private CTIService ctiService;

	/**
	 * 外呼
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/callout", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String voicecallOut(HttpServletRequest request, HttpServletResponse response) {
		User user = ShiroUtil.getUserInfo();
		// 进行外呼请求
		JSONObject jsonresult = JSONObject.fromObject(CallService
				.voicecallOut(user.getWorkno(), request.getParameter("called")));
		if ("0".equals(jsonresult.getString("retcode"))) {
			// 外呼成功     插入通讯记录  type：1 (外呼)
			AgentCall agentCall = new AgentCall(user.getUserNo(), user.getWorkno(), jsonresult.getString("result"), 
					user.getPhonenumber(), request.getParameter("called"), DateUtil.getDate(), 1, false);
			ctiService.insertAgentCall(agentCall);
		} else {
			// 外呼失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
		}
		// 返回信息
		return jsonresult.toString();
	}
	
	/**
	 * 内呼
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/callinner", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String voicecallInner(HttpServletRequest request, HttpServletResponse response) {
		// 进行内呼请求
		JSONObject jsonresult = JSONObject.fromObject(CallService
				.voicecallInner(ShiroUtil.getUserInfo().getWorkno(), request.getParameter("called")));
		if ("0".equals(jsonresult.getString("retcode"))) {
			// 内呼成功  
		} else {
			// 内呼失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
		}
		// 返回信息
		return jsonresult.toString();
	}
	
	/**
	 * 挂断呼叫
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/callrelease", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String voiceRelease(HttpServletRequest request, HttpServletResponse response) {
		// 进行挂断呼叫请求
		JSONObject jsonresult = JSONObject.fromObject(CallService
				.voiceRelease(ShiroUtil.getUserInfo().getWorkno()));
		if ("0".equals(jsonresult.getString("retcode"))) {
			// 挂断成功
		} else {
			// 挂断失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
		}
		// 返回信息
		return jsonresult.toString();
	}
	
	/**
	 * 挂断话机
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/callreleasePhone", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String voiceReleasePhone(HttpServletRequest request, HttpServletResponse response) {
		// 进行挂断呼叫请求
		JSONObject jsonresult = JSONObject.fromObject(CallService
				.voiceReleasePhone(ShiroUtil.getUserInfo().getWorkno()));
		if ("0".equals(jsonresult.getString("retcode"))) {
			// 挂断成功
		} else {
			// 挂断失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
		}
		// 返回信息
		return jsonresult.toString();
	}
	
	/**
	 * 呼叫应答
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/callAnswer", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String voiceAnswer(HttpServletRequest request, HttpServletResponse response) {
		// 进行呼叫应答请求
		JSONObject jsonresult = JSONObject.fromObject(CallService
				.voiceAnswer(ShiroUtil.getUserInfo().getWorkno()));
		if ("0".equals(jsonresult.getString("retcode"))) {
			// 呼叫应答成功
		} else {
			// 呼叫应答失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
		}
		// 返回信息
		return jsonresult.toString();
	}
	
	/**
	 * 呼叫转移
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/callTransfer", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String voiceTransfer(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> transferParam = new HashMap<String, Object>();        
        transferParam.put("devicetype", Integer.parseInt(request.getParameter("devicetype")));	// 转移设备类型，技能队列为1，业务代表为2，IVR设备为3，系统接入码为4，外呼号码为5 2
        transferParam.put("mode", 2);   														// 转移模式，在内部转移的情况下：释放转为0，挂起转为1，成功转为2，指定转为3，合并转为4；在外呼转移的情况下，释放转为1，成功转为2，通话转为3，三方转为4。 3
        transferParam.put("address", request.getParameter("address"));      					// 转移地址，即转移设备类型对应的设备ID。最大长度24
        transferParam.put("caller", request.getParameter("callNum"));    						// 主叫号码。（内容可为空，为空时为平台默认主叫号码，0-24位数字）。
        transferParam.put("callappdata", request.getParameter("callappdata"));    				// 需设置的随路数据。内容可为空，最大长度为1024
        
		// 进行呼叫转移
		JSONObject jsonresult = JSONObject.fromObject(CallService
				.voiceTransfer(ShiroUtil.getUserInfo().getWorkno(), transferParam));
		if ("0".equals(jsonresult.getString("retcode"))) {
			// 转移成功
		} else {
			// 转移失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
		}
		// 返回信息
		return jsonresult.toString();
	}

	/**
	 * 呼叫数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/calldata", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String Calldata(HttpServletRequest request, HttpServletResponse response){
		String result = "";
    	
		String type = request.getParameter("type"); //请求类型
		String workNo = ShiroUtil.getUserInfo().getWorkno(); //坐席工号
		
        switch(type) {
        
        //查询自身通话记录
        case GlobalConstant.CALLRECORDLIST:
        	Map<String, Object> searchMap = new HashMap<String, Object>();
        	searchMap.put("workNo", workNo);
        	searchMap.put("otherPhone", request.getParameter("otherPhone"));
        	searchMap.put("startDate", request.getParameter("startDate"));
        	searchMap.put("endDate", request.getParameter("endDate"));
        	searchMap.put("page",  Integer.parseInt(request.getParameter("page")));
        	searchMap.put("limit",  Integer.parseInt(request.getParameter("limit")));
        	result = ctiService.getCallRecordList(searchMap);
            break;
        
        //查询呼叫信息
        case GlobalConstant.CALLINFO:
        	result = CallService.callinfo(workNo);
            break;
            
        //查询指定坐席所有呼叫信息
        case GlobalConstant.ALLCALLINFO:
        	result = CallService.allcallinfo(workNo, workNo);
            break;
            
        //查询指定座席上通话方电话号码信息
        case GlobalConstant.REMOTENUMBERS:
        	result = CallService.remotenumbers(workNo);
            break;
            
        //查询所有呼叫CallID信息
        case GlobalConstant.ALLCALLID:
        	result = CallService.allcallid(workNo);
            break;
            
        //查询指定座席呼叫ID
        case GlobalConstant.ALLCALLINFOEX:
        	result = CallService.allcallinfo(workNo, workNo);
            break;
            
        //根据呼叫CallID查询呼叫信息
        case GlobalConstant.CALLINFOBYCALLID:
        	result = CallService.callinfobycallid(workNo);
            break;
            
        //查询应答来话前呼叫信息
        case GlobalConstant.CALLINFOBEFOREANSWER:
        	result = CallService.callinfobeforeanswer(workNo);
            break;
            
        //查询座席当前呼叫统计信息
        case GlobalConstant.STATISTICS:
        	result = CallService.statistics(workNo);
            break;
            
        default:
            break;
        }
		
    	JSONObject jsonresult = JSONObject.fromObject(result); //返回信息转Json
    	
        if("0".equals(jsonresult.getString("retcode"))){
        	// 操作成功
        }else {
            //操作失败
        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
        }
        
        // 返回信息
        return jsonresult.toString();
    }
}
