import org.kc.swaggerexample.{ExampleSwagger, SwaggerApiDoc, ExampleServlet}
import org.scalatra._
import javax.servlet.ServletContext


class ScalatraBootstrap extends LifeCycle {
  val Prefix = "/rest/"

  override def init(context: ServletContext) {

    val swagger = new ExampleSwagger

    context.mount(new ExampleServlet(swagger), Prefix + "example")
    context.mount(new SwaggerApiDoc(swagger), Prefix + "api-docs/*")

    context.put("example", this)
  }


}
