package message.service;

import com.alibaba.fastjson.JSONObject;
import consts.ConfigConsts;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 提醒已读
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerRemindDeleteTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("userId","001");
            jsonObject.put("projectId","prj001");
//            jsonObject.put("id","5efdaa0c4bffda00016d6860");
            jsonObject.put("bizId","biz32");
            jsonObject.put("year","2020");
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("iop-message-remind-delete-dev" /* Topic */,
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
