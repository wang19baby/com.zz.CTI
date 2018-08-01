package com.zz.CTI.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.CTI.Shiro.ShiroUtil;
import com.zz.CTI.Util.JsonMapper;
import com.zz.CTI.huawei.common.GlobalConstant;
import com.zz.CTI.huawei.service.AgentService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;

/**
 * 在线坐席
 * 
 * @author zhou.zhang
 *
 */
@Controller
@RequestMapping("/onlineagent")
public class OnlineagentController extends BaseController {
	@Resource
	private CTIService ctiService;

	/**
	 * 在线坐席
	 * 
	 * @param session
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/OnlineAgentServlet", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String OnlineAgentServlet(HttpServletRequest request, HttpServletResponse response) {
		
		String result = "";
		String type = request.getParameter("type"); // 请求类型
		String workNo = ShiroUtil.getUserInfo().getWorkno();

		switch (type) {

		// 示闲请求
		case GlobalConstant.READY:
			result = AgentService.ready(workNo);
			break;

		// 示忙请求
		case GlobalConstant.BUSY:
			result = AgentService.busy(workNo);
			break;

		// 工作请求
		case GlobalConstant.WORK:
			result = AgentService.work(workNo);
			break;

		// 退出工作请求
		case GlobalConstant.OUTWORK:
			result = AgentService.outwork(workNo);
			break;

		// 发布公告
		case GlobalConstant.NOTIFYBULLRTIN:
			result = AgentService.notifybulletin(workNo, request.getParameter("targetname"),
					request.getParameter("bulletindata"));
			break;

		default:
			break;
		}

		JSONObject jsonresult = JSONObject.fromObject(result); // 返回信息转Json

		if ("0".equals(jsonresult.getString("retcode"))) {
			// 操作成功
		} else {
			// 操作失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); // 返回错误描述
		}

		// 返回信息
		return JsonMapper.toJsonString(jsonresult);
	}
}
