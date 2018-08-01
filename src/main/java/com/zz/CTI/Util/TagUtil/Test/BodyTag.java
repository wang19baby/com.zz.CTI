package com.zz.CTI.Util.TagUtil.Test;

import java.io.IOException;  
import java.util.Date;  
  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.jsp.JspException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;  
  
public class BodyTag extends BodyTagSupport {  
  
//  执行顺序  
//  
//  doStartTag()->setBodyContent()->doInitBody()->doAfterTag()->doEndTag()  
//  
//  如果doStartTag()返回的是EVAL_BODY_INCLUDE执行doAfterTag()方法,  
//  
//  如果它返回SKIP_BODY就执行doEndTag()方法。  
//  
//  setBodyContent()方法用于设置标签体内容，如果在计算BodyContent时需要进行一些初始化工作，  
//  
//  则在doInitBody()方法中完成。标签体内容执行完后，会调用doAfterBody()方法  
//  
//  在doAfterTag()方法中返回EVAL_BODY_AGAIN来重复执行doAfterTag()方法  
//  
//  返回SKIP_BODY值则执行doEndTag()方法。  
//  
//  在doEndTag()方法中返回EVAL_PAGE值,则执行此标签的后的其它代码,  
//  
//  返回SKIP_PAGE则不执行此页面的其它代码。  
      
	private static final long serialVersionUID = 1L;

	private int count;  
  
    @SuppressWarnings("unused")
	private HttpServletRequest reqeust;  
  
    private JspWriter out;  
  
    public void init() {  
        reqeust = (HttpServletRequest) pageContext.getRequest();  
        out = pageContext.getOut();  
    }  
  
    @Override  
    public int doStartTag() throws JspException {  
        init();  
        return Tag.EVAL_BODY_INCLUDE;  
    }  
  
    // 设置当前标签体  
    @Override  
    public void setBodyContent(BodyContent bodyContent) {  
        this.bodyContent = bodyContent;  
        System.out.println("setBodyContent...");  
    }  
  
    // 需要初始化bodyContent  
    @Override  
    public void doInitBody() throws JspException {  
        System.out.println("init.....");  
    }  
  
    @Override  
    public int doAfterBody() throws JspException {  
        if (count >= 1) {  
            try {  
                out.println(count);  
                out.println("<Br>");  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            count--;  
            return IterationTag.EVAL_BODY_AGAIN;  
        } else {  
            return Tag.SKIP_BODY;  
        }  
    }  
  
    @Override  
    public int doEndTag() throws JspException {  
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd");  
        String date = formater.format(new Date());  
        try {  
            out.print(date);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return Tag.EVAL_PAGE;  
    }  
  
    // 必须实现setXX()方法  
    public void setCount(int count) {  
        this.count = count;  
    }  
  
}  
