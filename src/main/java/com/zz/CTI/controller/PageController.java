package com.zz.CTI.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zz.CTI.service.CTIService;

/**
 * 页面转发
 * @author zhou.zhang
 *
 */
@Controller
@RequestMapping("/page")
public class PageController extends BaseController {
	@Resource
	private CTIService ctiService;
	
	@RequestMapping(value="/main", method = RequestMethod.GET)
    public String Main(Model model) {
		return "main";
    }
	
	@RequestMapping(value="/callOut", method = RequestMethod.GET)
    public String callOut(Model model) {
		return "voiceCall/callOut";
    }
	
	@RequestMapping(value="/callRecordInfoList", method = RequestMethod.GET)
    public String callRecordInfoList(Model model) {
		return "voiceCall/callRecordInfoList";
    }
	
	@RequestMapping(value="/callOutLayer", method = RequestMethod.GET)
    public String callOutLayer(Model model) {
		return "voiceCall/callOutLayer";
    }
	
	@RequestMapping(value="/callData", method = RequestMethod.GET)
    public String callData(Model model) {
		return "voiceCall/callData";
    }
	
	/****************** Record ******************************/
	
	@RequestMapping(value="/recordList", method = RequestMethod.GET)
    public String recordList(Model model) {
		return "Record/recordList";
    }
	
	@RequestMapping(value="/recordPlay", method = RequestMethod.GET)
    public String recordPlay(Model model) {
		return "Record/recordPlay";
    }
	
	/****************** agentGroup ******************************/
	
	@RequestMapping(value="/agentByGroup", method = RequestMethod.GET)
    public String agentByGroup(Model model) {
		return "agentGroup/agentByGroup";
    }
	
	@RequiresPermissions( {"agentInfo"} )
	@RequestMapping(value="/agentInfo", method = RequestMethod.GET)
    public String agentInfo(Model model) {
		return "agentGroup/agentInfo";
    }
	
	@RequestMapping(value="/agentGroupList", method = RequestMethod.GET)
    public String agentGroupList(Model model) {
		return "agentGroup/agentGroupList";
    }
	
	/**
	 * 坐席转接页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/agentTransfer", method = RequestMethod.GET)
    public String agentTransfer(Model model) {
		return "agentGroup/agentTransfer";
    }
	
	/**
	 * 公司坐席转接页面
	 * @param model
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin", "Admin", "Receptionist" }, logical = Logical.OR)
	@RequestMapping(value="/agentTransferByReceptionist", method = RequestMethod.GET)
    public String agentTransferByReceptionist(Model model) {
		return "agentGroup/agentTransferByReceptionist";
    }
	
	/*********************** agentManager *************************************/
	
	/**
	 * 用户列表
	 * @param model
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin", "Admin", "Receptionist" }, logical = Logical.OR)
	@RequestMapping(value="/agentList", method = RequestMethod.GET)
    public String agentList(Model model) {
		model.addAttribute("agentRoles", ctiService.getAgentRoles());
		model.addAttribute("agentPermissions", ctiService.getAgentPermissions());
		return "agentMananger/agentList";
    }
	
	/**
	 * 增加用户
	 * @param model
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin" })
	@RequestMapping(value="/addAgent", method = RequestMethod.GET)
    public String addAgent(Model model) {
		return "agentMananger/addAgent";
    }
	
	/**
	 * 编辑用户
	 * @param model
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin", "Admin", "Receptionist" }, logical = Logical.OR)
	@RequestMapping(value="/editUser", method = RequestMethod.GET)
    public String editUser(Model model, HttpServletRequest request, 
    		HttpServletResponse response) {
		model.addAttribute("agentUser", ctiService.getAgentUserByUserNo(request.getParameter("userNo")));
		return "agentMananger/editUser";
    }
	
	/*********************** SYS *************************************/
	
	/**
	 * 403
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forbidden", method = RequestMethod.GET)
    public String forbidden(Model model) {
		return "sys/403";
    }
	
	/**
	 * 404
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/notFound", method = RequestMethod.GET)
    public String notFound(Model model) {
		return "sys/404";
    }
}
