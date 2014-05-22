# Flume-Kafka-Sink

Kafka作为一个Flume的Sink。

原理很简单，从Flume的channel中读出Event，将其发到Kafka中。默认情况下，Event是单条依次发送的；如果需要批量的方式，将通过Kafka的Producer配置来实现。配置详情可见：https://kafka.apache.org/documentation.html#producerapi

**Flume配置：**

配置中，以kafka\_producer\_开头的参数将作为Kafka的参数传入Kafka Producer的Factory类中。

**最小配置：**

```
xxx.sinks.{name}.kafka_topic = {topic}
xxx.sinks.{name}.kafka_producer_metadata.broker.list = {brokers}
xxx.sinks.{name}.kafka_producer_serializer.class = kafka.serializer.StringEncoder
```
