
import java.io.File;
import javax.xml.transform.TransformerConfigurationException;


public class Main {

    
    public static void main(String[] args) throws TransformerConfigurationException {
      
        /*
        File f=new File("Libros.xml");
        AccessXMLDOM acceso=new AccessXMLDOM();
        
        acceso.abriXMLaDOM(f);
        System.out.println("");
        acceso.recorreDOMyMuestra();
        
        
        String titulo="Dublineses";
        String autor="James Joyce";
        String fecha="1914";
        
        acceso.insertarLibroEnDOM(titulo, autor, fecha);
        acceso.recorreDOMyMuestra();
        System.out.println("");
        
        acceso.deleteNode(titulo);
        
        

*/
        
        AccessXMLDOM acceso=new AccessXMLDOM(); //creamos objeto de la clase AccessXMLDOM
        File f=new File("Libros.xml"); //creamos  un objeto de tipo File  con el xml "Libros.xml"
        
        acceso.abriXMLaDOM(f); //lo abrimos 
        acceso.recorreDOMyMuestra(); //lo recorremos y mostramos 
        acceso.insertarLibroEnDOM("Yerma", "Lorca", "1935"); //insertamos un libro
        acceso.deleteNode("Don Quijote"); //borramos un nodo
        acceso.guardarDOMcomoArchivo("LibrosDeDom.xml"); //guardamos en nuevo DOM en un XML
         
        
        
    }
    
}
