package com.wzh.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * JMS消息发送
 * @author Administrator
 */
public class JmsSender {
    
    JmsTemplate jmsTemplate;
    
    public void setJmsTemplate(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }
    
    
    public String sendMsg(final String msg) {
        System.out.println("准备发送数据============>>>："+msg);
        Message replyMsg = this.jmsTemplate.sendAndReceive(new MessageCreator(){
            public Message createMessage(Session sn) throws JMSException {
                TextMessage txtMsg = sn.createTextMessage(msg);
                return txtMsg;
            }
        });       
        TextMessage txtMsg = (TextMessage)replyMsg;
        
        String result = null;
        
        try {
            result = txtMsg.getText();
            System.out.println("获取到返回值==============>>>："+result);
        } catch (JMSException ex) {
            Logger.getLogger(JmsSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
