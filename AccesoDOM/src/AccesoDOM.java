//Ejercicio 1.1


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;//for Document
import org.w3c.dom.Document;
import java.util.*;
import java.io.*;//clase File




public class AccesoDOM {
     
     
    Document doc;
    public int abriXMLaDOM (File f){
        //Con este método abrimos el archivo xml y generamos el DOM mediante un objeto de tipo File
        //que le pasamos por parámetro. Para ello, usamos las clases DocumentBuilder y DocumentBuilder factory
        try{
              System.out.println("Abriendo archivo XML file y generando DOM...");
                 DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
                 factory.setIgnoringComments(true); //con esto, se ignoran comentarios
                 factory.setIgnoringElementContentWhitespace(true); //con esto, se ignoran espacios en blanco
                 DocumentBuilder builder=factory.newDocumentBuilder();
                 doc=builder.parse(f);
                 System.out.println("DOM creado con éxito.");
              
               
        return 0;
    } catch (Exception e) {
            System.out.println(e);
            return -1;
    }
        
    }
    
    
}
