package com.zz.CTI.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zz.CTI.Bean.AgentRecord;
import com.zz.CTI.Shiro.ShiroUtil;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.huawei.common.GlobalConstant;
import com.zz.CTI.huawei.service.RecordService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;

/**
 * 录音
 * @author zhou.zhang
 *
 */
@Controller
@RequestMapping("/record")
public class RecordControler extends BaseController {
	@Resource
	private CTIService ctiService;
	
	@RequestMapping(value="/controller", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String record(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		String workNo = ShiroUtil.getUserInfo().getWorkno();
		
		switch (request.getParameter("type")) {
		
		case GlobalConstant.RECORDLIST:
			// 录音列表
        	Map<String, Object> searchMap = new HashMap<String, Object>();
        	searchMap.put("userNo", ShiroUtil.getUserInfo().getUserNo());
        	searchMap.put("startDate", request.getParameter("startDate"));
        	searchMap.put("endDate", request.getParameter("endDate"));
        	searchMap.put("page",  Integer.parseInt(request.getParameter("page")));
        	searchMap.put("limit",  Integer.parseInt(request.getParameter("limit")));
        	result = ctiService.getAgentRecordList(searchMap);
			break;
		
		case GlobalConstant.UPDATERECORD:
			// 更新录音
			result = ctiService.updateAgentRecord(new AgentRecord(request.getParameter("recordId"), request.getParameter("callId"), 
					null, workNo, request.getParameter("recordTitle"), null, null, request.getParameter("recordEndDate"), null));
			break;
			
		case GlobalConstant.RECORDPLAY:
			// 录音回放
			JSONObject jsonresult = JSONObject.fromObject(ctiService.getAgentRecordByRecordId(request.getParameter("recordId")));
			if (("0".equals(jsonresult.getString("retcode")))) {
				result = RecordService.recordPlay(workNo, jsonresult.getJSONObject("result").getString("fileName"));
			} else {
				result = jsonresult.toString();
			}
			break;
			
		case GlobalConstant.STOPPLAY:
			// 停止录音播放
			result = RecordService.recordStopPlay(workNo);
			break;
			
		case GlobalConstant.PAUSEPLAY:
			// 暂停放音
			result = RecordService.recordPausePlay(workNo);
			break;
			
		case GlobalConstant.RESUMEPLAY:
			// 恢复放音
			result = RecordService.recordResumePlay(workNo);
			break;
			
		case GlobalConstant.RECORDFOREFAST:
			// 放音快进
			result = RecordService.recordForeFast(workNo, request.getParameter("fastTime"));
			break;
			
		case GlobalConstant.RECORDBACKFAST:
			// 放音快退
			result = RecordService.recordBackFast(workNo, request.getParameter("fastTime"));
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
	
	@RequestMapping(value="/play")  
    public ModelAndView getAudio(HttpServletRequest request, HttpServletResponse response) throws IOException { 
        String range = request.getHeader("Range");
        String[] rs = range.split("\\=");
        range = rs[1].split("\\-")[0];
                
        File file = new File("D:/测试/WAV/" + request.getParameter("file") + ".wav");
        
        if(!file.exists()) {
        	Logs.error(request.getSession().getAttribute("workno") + ": " + 
        request.getParameter("file") + ".wav 音频文件不存在 --> 404");
        }

        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        long length = file.length();
        // 播放进度  
        int count = 0;
        // 播放百分比  
        int percent = (int)(length * 1);
  
        int irange = Integer.parseInt(range);  
        length = length - irange;  
  
        response.addHeader("Accept-Ranges", "bytes");  
        response.addHeader("Content-Length", length + "");  
        response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);  
        response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");  
  
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            os.write(b, 0, len);  
            count += len;
            if(count >= percent){
                break;
            }  
        }
        
        fis.close();  
        os.close();
        return null;  
    }
}
