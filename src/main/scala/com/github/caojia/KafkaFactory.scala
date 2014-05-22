package com.github.caojia

import kafka.producer.{ProducerConfig, Producer}
import scala.collection.convert.wrapAsScala._
import java.util.Properties
import org.apache.flume.Context

/**
 * Created by caojia on 5/21/14.
 */
object KafkaProducer {
  def apply[T, S](context: Context): Producer[T, S] = {
    val props: Properties = new Properties()
    // see https://kafka.apache.org/documentation.html#producerapi
    context.getParameters.foreach({
      case (key, value) => if (key.startsWith("kafka_producer_")) {
        props.setProperty(key.replaceFirst("kafka_producer_", ""), value)
      }
    })
    val producerConfig = new ProducerConfig(props)
    new Producer[T, S](producerConfig)
  }

}
