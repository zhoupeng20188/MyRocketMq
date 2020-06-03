import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 顺序消息消费者
 */
public class OrderedConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_group");
        consumer.setNamesrvAddr(ConfigConsts.rocket_host);
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("orderTest", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {

//            AtomicLong consumeTimes = new AtomicLong(0);
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                System.out.println("================consumeMessage in ===============");
                for (MessageExt msg : msgs) {
                    System.out.println(Thread.currentThread().getName() + " Receive New Messages: "+
                            new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
//                context.setAutoCommit(false);
//
//                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
//                this.consumeTimes.incrementAndGet();
//                if ((this.consumeTimes.get() % 2) == 0) {
//                    return ConsumeOrderlyStatus.SUCCESS;
//                } else if ((this.consumeTimes.get() % 3) == 0) {
//                    return ConsumeOrderlyStatus.ROLLBACK;
//                } else if ((this.consumeTimes.get() % 4) == 0) {
//                    return ConsumeOrderlyStatus.COMMIT;
//                } else if ((this.consumeTimes.get() % 5) == 0) {
//                    context.setSuspendCurrentQueueTimeMillis(3000);
//                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
//                }
//                return ConsumeOrderlyStatus.SUCCESS;

            }
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}