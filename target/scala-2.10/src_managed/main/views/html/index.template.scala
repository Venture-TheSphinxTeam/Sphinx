
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

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">

    <title>Jumbotron Template for Bootstrap</title>

    
    <!-- Custom styles for this template -->
    <link href="jumbotron.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h1>Welcome to Sphinx</h1>
        <p>The project management system that brings projects and people together.</p>
        <p><a class="btn btn-primary btn-lg">Get started &raquo;</a></p>
      </div>
    </div>

    <div class="container">
    <div class="row">  
    <div class="span4">  
    <div class="tabbable">  
    <ul class="nav nav-tabs">  
    <li class="active"><a href="#1" data-toggle="tab">My Projects</a></li>  
    <li class=""><a href="#2" data-toggle="tab">Team Projects</a></li>  
    <li class=""><a href="#3" data-toggle="tab">Projects Across the Firm</a></li>  
    </ul>  
    <div class="tab-content">  
    <div class="tab-pane active" id="1">  
    <br/>
    <ul class="thumbnails">
  
      <div class="thumbnail">
        <h3>Initiative 1:</h3>
        <p>Due date has changed<br/>
        10/24/13 11:00 PM
        <br/>
        <a class="btn btn-primary btn-sm">Learn more &raquo;</a>
        </p>
      </div>
      <br/>
      <div class="thumbnail">
      <h3>Initiative 2:</h3>
      <p>New member has been added
      <br/> 10/25/13 4:00 PM
      <br/>
      <a class="btn btn-primary btn-sm">Learn more &raquo;</a></p>
    </div>
  </ul>  
    </div>  
    <div class="tab-pane" id="2">  
    <p>You are watching Section 2.</p>  
    </div>  
    <div class="tab-pane" id="3">  
    <p>You are watching Section 3.</p>  
    </div>  
    </div>  
    </div>  
    </div>  
    </div>  
    
      </div>

      <hr>

      <footer>
        <p>&copy; Team Venture</p>
      </footer>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../../assets/js/jquery.js"></script>
   
  </body>
</html>

    

""")))})),format.raw/*103.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Oct 25 15:10:06 EDT 2013
                    SOURCE: /home/striker/work/Sphinx/app/views/index.scala.html
                    HASH: cd48682b74d2003b698936ef5f6190824e3d231f
                    MATRIX: 774->1|885->18|922->21|975->66|1014->68|3709->2731
                    LINES: 26->1|29->1|31->3|31->3|31->3|131->103
                    -- GENERATED --
                */
            