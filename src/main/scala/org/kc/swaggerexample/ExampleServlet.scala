package org.kc.swaggerexample

import org.scalatra.swagger.{SwaggerSupport, Swagger}
import org.scalatra.json.{JValueResult, JacksonJsonSupport}
import org.json4s.{DefaultFormats, Formats}
import java.util.Date
import org.scalatra._

class ExampleServlet(val swagger: Swagger) extends ScalatraServlet with ExampleServletSwaggerDefinition with JacksonJsonSupport with JValueResult {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/", operation(getOperation)) {
    val itemType = params("type")
    ExampleItemList(List(
      ExampleItem("1", "item1", itemType, new Date()),
      ExampleItem("2", "item2", itemType, new Date())
    ))
  }

  get("/:id", operation(getDetailsOperation)) {
    val id = params("id")
    ExampleItemDetails(id, "item" + id, "product", Owner(1, "Owner name", true), new Date())
  }

  post("/", operation(addItemOperation)) {
    val newItemRequest = parsedBody.extract[NewItemRequest]
    ExampleItemDetails("new-item-id", newItemRequest.name, "product", Owner(1, "Owner name", true), new Date())
  }

}

trait ExampleServletSwaggerDefinition extends SwaggerSupport {

  override protected val applicationName = Some("example")
  protected val applicationDescription = "Sample endpoint with services"

  val getOperation = apiOperation[ExampleItemList]("getAllItems")
    .parameter(queryParam[String]("type").description("Filter value for item type").optional)
    .summary("Gets all example items")

  val getDetailsOperation = apiOperation[ExampleItemDetails]("getItemDetails")
    .parameter(pathParam[String]("id").description("Item identifier").required)
    .summary("Returns all details of item with given identifier")

  val addItemOperation = apiOperation[ExampleItemDetails]("addNewItem")
    .parameter(bodyParam[NewItemRequest]("newItem").description("New item definition").required)
    .summary("Creates a new item and returns its details")

}

case class ExampleItemList(items: List[ExampleItem])

case class ExampleItem(id: String, name: String, itemType: String, expiryDate: Date)

case class Owner(id: Long, name: String, isValid: Boolean)

case class ExampleItemDetails(id: String, name: String, itemType: String, owner: Owner, expiryDate: Date)

case class NewItemRequest(name: String)