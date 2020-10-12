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
 * 公告插入
 * @Author zp
 * @create 2019/9/16 10:05
 */
public class ProducerAnnounceInsertTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("iop-message-producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(ConfigConsts.rocket_host);
        //Launch the instance.
        producer.start();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 24; i < 25; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("createTime",new Date());
//            jsonObject.put("createTime",format.parse("2019-09-11 15:00:00"));
            jsonObject.put("projectId","prj001");
//            jsonObject.put("projectId","prj001");
//            jsonObject.put("userGroup","group002");
            jsonObject.put("title","公告"+i);
            jsonObject.put("content","公告内容"+i);
            jsonObject.put("bizId","biz"+i);
//            jsonObject.put("isAppPush",true);
//            jsonObject.put("appPushTitle","标题1");
//            jsonObject.put("appPushSubTitle","公告"+i);
//            jsonObject.put("appPushContent","公告内容"+i);
//            jsonObject.put("appPushExtraParam","system|公告内容"+i);
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("iop-message-announce-insert-dev" /* Topic */,
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
