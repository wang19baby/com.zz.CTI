package com.zz.CTI.huawei.common;

public class GlobalConstant {
    public static final String LOGIN = "LOGIN"; //签入
    
    public static final String FORCELOGIN = "FORCELOGIN"; //强制签入
    
    public static final String LOGOUT = "LOGOUT"; //签出

    public static final String READY = "READY"; //示闲
    
    public static final String BUSY = "BUSY"; //示忙
    
    public static final String WORK = "WORK"; //工作
    
    public static final String OUTWORK = "OUTWORK"; //退出工作
    
    public static final String AGENTSKILL = "AGENTSKILL"; //查询坐席配置配置队列
    
    public static final String AGENTBYWORKNO = "AGENTBYWORKNO"; //查询指定座席详细座席信息
    
    public static final String ONLINEAGENTONVDNBYKEYWORD = "ONLINEAGENTONVDNBYKEYWORD"; //关键字模糊查询在线座席
    
    public static final String AGENTINFO = "AGENTINFO"; //批量查询坐席列表信息
    
    public static final String CALLRECORDLIST = "CALLRECORDLIST"; //查询自身通话记录
    
    public static final String CALLINFO = "CALLINFO"; //查询呼叫休息
    
    public static final String ALLCALLINFO = "ALLCALLINFO"; //查询指定坐席所有呼叫信息
    
    public static final String GROUPBYAGENT = "GROUPBYAGENT"; //查询指定座席上通话方电话号码信息
    
    public static final String REMOTENUMBERS = "REMOTENUMBERS"; //查询指定座席上通话方电话号码信息
    
    public static final String ALLCALLID = "ALLCALLID"; //查询所有呼叫CallID信息
    
    public static final String ALLCALLINFOEX = "ALLCALLINFOEX"; //查询指定座席呼叫ID
    
    public static final String CALLINFOBYCALLID = "CALLINFOBYCALLID"; //根据呼叫CallID查询呼叫信息
    
    public static final String CALLINFOBEFOREANSWER = "CALLINFOBEFOREANSWER"; //查询应答来话前呼叫信息
    
    public static final String STATISTICS = "STATISTICS"; //查询座席当前呼叫统计信息
    
    public static final String AGENTONGROUPEX = "AGENTONGROUPEX"; //查询指定班组的座席信息
    
    public static final String ALLAGENTSTATUS = "ALLAGENTSTATUS"; //查询所在VDN所有座席当前状态信息
    
    public static final String ONLIGNEAGENTONVDN = "ONLIGNEAGENTONVDN"; //查询所属VDN在线座席信息
    
    public static final String ONLIGNEAGENTGROUP = "ONLIGNEAGENTGROUP"; //查询指定班组的在线坐席信息
    
    public static final String AGENTBYGROUP = "AGENTBYGROUP"; //查询所属班组的坐席信息
    
    public static final String GROUPONVDN = "GROUPONVDN"; //查询所在VDN所有班组信息
    
    public static final String NOTIFYBULLRTIN = "NOTIFYBULLRTIN"; //发布公告
    
    public static final String RECORDLIST = "RECORDLIST"; //录音列表
    
    public static final String RECORDFILE = "RECORDFILE"; //录音查询
    
    public static final String RECORDPLAY = "RECORDPLAY"; //录音回放
    
    public static final String STOPPLAY = "STOPPLAY"; //停止播放
    
    public static final String PAUSEPLAY = "PAUSEPLAY"; //暂停放音
    
    public static final String RESUMEPLAY = "RESUMEPLAY"; //恢复放音
    
    public static final String RECORDFOREFAST = "RECORDFOREFAST"; //放音快进
    
    public static final String RECORDBACKFAST = "RECORDBACKFAST"; //放音快退
    
    public static final String UPDATERECORD = "UPDATERECORD"; //更新录音

    public static final String RESETSKILL = "RESETSKILL";    

    public static final String REMOVEWORKNO = "REMOVEWORKNO";
    
    public static final String ANSWER = "ANSWER";
    
    public static final String RELEASE = "RELEASE";
    
    public static final String SAVECALLID = "SAVECALLID";
    
    public static final String DELETECALLID = "DELETECALLID";
    
    public static final String HOLD = "HOLD";

    public static final String GETHOLD = "GETHOLD";

    public static final String MUTE = "MUTE";   
    
    public static final String CANCLEMUTE = "CANCLEMUTE";

    public static final String CALLINNER = "CALLINNER";

    public static final String CALLOUT = "CALLOUT";

    public static final String TRANSTOAGENT = "TRANSTOAGENT";
    
    public static final String TRANSTOSKILLS = "TRANSTOSKILLS";
    
    public static final String SENDMESSAGE = "SENDMESSAGE";
    
    public static final String BACK_TYPE_SUCCESS = "0";
}
