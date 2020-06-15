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
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("createTime",new Date());
//            jsonObject.put("createTime",format.parse("2019-09-11 15:00:00"));
            jsonObject.put("projectId","prj001");
            jsonObject.put("title"+i,"到期提醒标题"+i);
            jsonObject.put("content"+i,"到期提醒内容"+i);
            jsonObject.put("bizId","biz"+i);
            jsonObject.put("action", "remind");
            jsonObject.put("targetUserId", "001");
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
