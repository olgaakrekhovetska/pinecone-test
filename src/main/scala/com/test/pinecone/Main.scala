package com.test.pinecone

import com.test.pinecone.PineconeIndexConfig.IndexInfraConfig
import io.pinecone.{PineconeClientConfig, PineconeIndexOperationClient}

import scala.util.{Failure, Success, Try}

object Main {
  type Error = String

  private val pineconeApiKey = "specify API key"
  private val pineconeEnv    = "us-east4-gcp"
  private val indexName      = "specify index name"
  private val projectName    = "specify project ID"

  def main(args: Array[String]): Unit = {
    implicit val clientConfig = new PineconeClientConfig()
      .withApiKey(pineconeApiKey)
      .withEnvironment(pineconeEnv)
      .withProjectName(projectName)

    implicit val client = new PineconeIndexOperationClient(clientConfig)

    println(s"Creating index `$indexName`")

    val indexConfig = PineconeIndexConfig(
      indexName,
      IndexInfraConfig("p2.x1", 1, 1, "dotproduct", 128, List("min_credit_cost", "min_payout_cost", "categories")),
      List(1)
    )

    //now create index
    createIndex(indexConfig)
  }

  private def createIndex(
      index: PineconeIndexConfig
  )(implicit client: PineconeIndexOperationClient): Unit = {
    val req = index.toCreateIndexReq
    Try(client.createIndex(req)) match {
      case Failure(excp) =>
        val errorMsg = excp.getMessage
        excp.printStackTrace()
        println(s"Failed to create index `${index.name}`, error: `$errorMsg`")
      case Success(_) =>
        println(s" Index `${index.name}` is created successfully.")
    }
  }

}
