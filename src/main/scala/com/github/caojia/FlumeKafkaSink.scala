package com.github.caojia

import org.apache.flume.conf.{ConfigurationException, Configurable}
import org.apache.flume.sink.AbstractSink
import kafka.producer.{KeyedMessage, Producer}
import org.apache.flume.Sink.Status
import org.apache.flume._

/**
 * Created by caojia on 5/21/14.
 */
class FlumeKafkaSink extends AbstractSink with Configurable {

  private var kafkaTopic: String = _
  private var kafkaProducer: Producer[String, String] = _

  override def configure(context: Context): Unit = {
    kafkaTopic = context.getString("kafka_topic")
    if (kafkaTopic == null || kafkaTopic.isEmpty) {
      throw new ConfigurationException("kafka_topic is not set")
    }
    kafkaProducer = KafkaProducer(context)
  }

  override def process(): Status = {

    val channel: Channel = getChannel
    val transaction: Transaction = channel.getTransaction
    var result: Status = Status.READY
    var event: Event = null

    // NOTE: if kafka is using async producer, the transaction can't be guaranteed
    try {
      transaction.begin
      event = channel.take
      if (event != null) {
        // TODO: how to define the key?
        val message = new KeyedMessage[String, String](
          topic = kafkaTopic,
          message = new String(event.getBody))
        kafkaProducer.send(message)
      }
      else { result = Status.BACKOFF }
      transaction.commit
    }
    catch {
      case ex: Exception => {
        transaction.rollback
        throw new EventDeliveryException("Failed to send event: " + event, ex)
      }
    }
    finally {
      transaction.close
    }

    return result
  }
}
