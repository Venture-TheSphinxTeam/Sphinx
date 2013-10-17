
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(Seq[Any](/*3.2*/main("Sphinx: Connecting Projects to People")/*3.47*/ {_display_(Seq[Any](format.raw/*3.49*/("""

    """),_display_(Seq[Any](/*5.6*/play20/*5.12*/.welcome(message, style = "Java"))),format.raw/*5.45*/("""

""")))})),format.raw/*7.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Oct 17 17:29:21 EDT 2013
                    SOURCE: /home/striker/work/Sphinx/app/views/index.scala.html
                    HASH: e7e5449eb3b30f45533fa93260c62cefcdc39f69
                    MATRIX: 774->1|885->18|922->21|975->66|1014->68|1055->75|1069->81|1123->114|1156->117
                    LINES: 26->1|29->1|31->3|31->3|31->3|33->5|33->5|33->5|35->7
                    -- GENERATED --
                */
            