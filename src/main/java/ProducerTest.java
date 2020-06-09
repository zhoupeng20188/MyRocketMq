import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Date;

/**
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
        for (int i = 1; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("createTime",new Date());
            jsonObject.put("projectId","prj001");
            jsonObject.put("title"+i,"公告"+i);
            jsonObject.put("content"+i,"公告内容"+i);
            jsonObject.put("bizId","biz"+i);
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("iop-message-announce-insert" /* Topic */,
                    "TagA" /* Tag */,
                    jsonObject.toJSONString().getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
