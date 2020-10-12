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
 * 提醒插入（到期提醒）
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerRemindInsertTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
//        producer.setNamesrvAddr(ConfigConsts.rocket_host_fat);
//        //Launch the instance.
//        producer.start();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 15; i < 16; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("createTime",new Date());
//            jsonObject.put("createTime",format.parse("2019-09-11 15:00:00"));
            jsonObject.put("projectId","prj100");
            jsonObject.put("title","到期提醒标题"+i);
            jsonObject.put("content","到期提醒内容"+i);
            jsonObject.put("bizId","biz"+i);
            jsonObject.put("action", "remind");
            jsonObject.put("targetUserId", "001");
//            jsonObject.put("isRead", false);
//            jsonObject.put("isMailSend", true);
//            jsonObject.put("mailAddress", "287214764@qq.com");
//            jsonObject.put("mailTitle", "MQ联调测试");
//            jsonObject.put("mailContent", "<h1>MQ联调测试内容</h1>");
//            jsonObject.put("readTime", null);
            //Create a message instance, specifying topic, tag and message body.

            System.out.println(jsonObject.toString());
            Message msg = new Message("iop-message-remind-insert-fat" /* Topic */,
                    "TagA" /* Tag */,
                    jsonObject.toJSONString().getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(500);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
