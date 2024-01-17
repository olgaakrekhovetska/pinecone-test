package com.test.pinecone

import com.test.pinecone.PineconeIndexConfig.IndexInfraConfig
import io.pinecone.model.{CreateIndexRequest, IndexMetadataConfig}

case class PineconeIndexConfig(name: String, configuration: IndexInfraConfig, doc_types: List[Int]) {
  def toCreateIndexReq(): CreateIndexRequest = {
    import scala.jdk.CollectionConverters._
    new CreateIndexRequest()
      .withIndexName(name)
      .withPodType(configuration.pod_type)
      .withPods(configuration.pods)
      .withReplicas(configuration.replicas)
      .withMetric(configuration.metric)
      .withDimension(configuration.dimension)
      .withMetadataConfig(new IndexMetadataConfig().indexed(configuration.indexed_fields.asJava))
  }
}
object PineconeIndexConfig {
  case class IndexInfraConfig(
      pod_type: String,
      pods: Int,
      replicas: Int,
      metric: String,
      dimension: Int,
      indexed_fields: List[String]
  )

}
