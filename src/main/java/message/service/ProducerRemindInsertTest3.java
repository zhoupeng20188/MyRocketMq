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
 * 提醒插入（订阅）
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerRemindInsertTest3 {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 32; i < 33; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("createTime",new Date());
//            jsonObject.put("createTime",format.parse("2019-09-11 15:00:00"));
            jsonObject.put("projectId","prj001");
            jsonObject.put("senderUserId","00"+i);
            jsonObject.put("senderUserName","test00"+i);
            jsonObject.put("bizId","biz"+i);
            jsonObject.put("action", "subscribe");
            jsonObject.put("targetId", "aid001");
            jsonObject.put("targetType", "文章");
            jsonObject.put("targetName", "大国战略");
            jsonObject.put("targetUserId", "001");
            jsonObject.put("isSmsSend", true);
            jsonObject.put("phone", "15971431252");
            jsonObject.put("isRead", false);
//            jsonObject.put("readTime", null);
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("iop-message-remind-insert" /* Topic */,
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
