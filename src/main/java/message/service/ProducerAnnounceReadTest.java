package message.service;

import com.alibaba.fastjson.JSONObject;
import consts.ConfigConsts;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 公告已读
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerAnnounceReadTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("projectId","prj001");
            jsonObject.put("userId","001");
            jsonObject.put("announceId","5ee1e6565e60060b08442ea5");
            jsonObject.put("year","2020");
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("iop-message-announce-read" /* Topic */,
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
