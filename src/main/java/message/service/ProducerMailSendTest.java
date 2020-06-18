package message.service;

import com.alibaba.fastjson.JSONObject;
import consts.ConfigConsts;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 邮件发送
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerMailSendTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mailAddress","287214764@qq.com");
            jsonObject.put("mailTitle","邮件测试5");
            jsonObject.put("mailContent","<h1>邮件测试内容</h1><a href='http://www.baidu.com'>百度</a>");
            jsonObject.put("bizId","biz5");
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("iop-mail-message-send" /* Topic */,
                    "TagA" /* Tag */,
                    jsonObject.toJSONString().getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
