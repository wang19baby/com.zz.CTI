package com.zz.CTI.huawei.bean;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>Title: 代理事件队列 </p>
 * <p>Description: Agent event queue </p>
 */
public class ProcessMessageQueue
{
    private Queue<String> messageQueue = new ConcurrentLinkedQueue<String>();

    public void putMessage(String message)
    {
        messageQueue.add(message);
    }

    public String getMessage()
    {
        
        if( messageQueue.size() > 0)
        {
            return messageQueue.poll();
        }
        return null;
    }

}
