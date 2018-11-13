package com.wzh.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.JmsUtils;

/**
 * JMS消息池内容获取
 * @author Administrator
 */
public class JmsReceiver implements SessionAwareMessageListener<TextMessage> {

    public void onMessage(TextMessage m, Session sn) {
        MessageProducer producer = null;
        TextMessage txtMsg;
        
        try {
            String param = m.getText();
            System.out.println("接收到数据=========>>>："+param);
            //在这里写对param的处理
            param=param+"数据已处理";
            
            txtMsg = sn.createTextMessage(param);
            
            producer = sn.createProducer(null);
            producer.send(m.getJMSReplyTo(), txtMsg);
        } catch (JMSException ex) {
            Logger.getLogger(JmsReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JmsUtils.closeMessageProducer(producer);
        }
    }
}