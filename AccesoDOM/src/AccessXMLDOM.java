//Ejercicio 1.2, 1.3 y 1.4

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AccessXMLDOM {

    Document doc;

    public int abriXMLaDOM(File f) {
        //Con este método abrimos el archivo xml y generamos el DOM mediante un objeto de tipo File
        //que le pasamos por parámetro. Para ello, usamos las clases DocumentBuilder y DocumentBuilder factory

        try {
            System.out.println("Abriendo archivo XML file y generando DOM...");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true); //con esto, se ignoran comentarios
            factory.setIgnoringElementContentWhitespace(true); //con esto, se ignoran espacios en blanco
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            System.out.println("DOM creado con éxito.");

            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }

    }

    public void recorreDOMyMuestra() {
        //Con este método, recorremos el DOM y mostramos el contenido de cada línea.
        //Para ello, usamos las clases Node y NodeList
        String[] datos = new String[3]; //en este array guardamos la información  de cada libro según el índice
        Node nodo = null;
        Node raiz = doc.getFirstChild(); //obtenemos el nodo raíz
        NodeList listaNodos = raiz.getChildNodes(); //obtenemos los hijos del nodo raíz

        for (int i = 0; i < listaNodos.getLength(); i++) { //recorremos la lista de nodos

            nodo = listaNodos.item(i); //guardamos que hay en la posición i de la lista

            if (nodo.getNodeType() == Node.ELEMENT_NODE) { //si el nodo es correcto

                Node ntemp = null;
                int contador = 1;

                datos[0] = nodo.getAttributes().item(0).getNodeValue(); //guardamos el valor del atributo en la posición cero
                NodeList nl2 = nodo.getChildNodes();

                for (int j = 0; j < nl2.getLength(); j++) { //en este bucle recorremos todos los nodos de un libro 
                    //en concreto y le asignamos valor a los índices del array de String (menos al índice 0, que es el atributo

                    ntemp = nl2.item(j); //asignamos a ntem el ítem del índice de j

                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) { //si el nodo es correcto

                        datos[contador] = ntemp.getTextContent(); //guardamos el valor del atributo.
                        //en la primera «vuelta», se  guarda el valor del índice 1.
                        contador++;

                    }

                }
                //System.out.println(datos[0]+"--"+datos[2]+"--"+datos[1]);
                System.out.println("Título: " + datos[1] + "\n");
                System.out.println("Año de publicación: " + datos[0] + "\n");
                System.out.println("Autor: " + datos[2] + "\n");
                System.out.println("--------");
                System.out.println("");

            }

        }

    }

    public int insertarLibroEnDOM(String titulo, String autor, String fecha) {
        //con este método, introducimos un nuevo libro en el DOM mediante una serie de parámetros
        //dados (titulo, autor y fecha). 

        try {
            System.out.println("Añadir libro al árbol DOM: " + titulo + "; " + autor + "; " + fecha);
            //TÍTULO
            Node ntitulo = doc.createElement("Título");
            Node ntitulo_text = doc.createTextNode(titulo);
            ntitulo.appendChild(ntitulo_text);

            //AUTOR
            Node nautor = doc.createElement("Autor");
            Node nautor_text = doc.createTextNode(autor);
            nautor.appendChild(nautor_text);

            //LIBRO
            Node nLibro = doc.createElement("Libro");
            ((Element) nLibro).setAttribute("publicado", fecha);
            nLibro.appendChild(ntitulo);
            nLibro.appendChild(nautor);
            //añadimos el atributo (fecha de publicación)
            nLibro.appendChild(doc.createTextNode("\n"));
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nLibro);
            System.out.println("Libro insertado en DOM.");
            return 0;
        } catch (Exception e) {
            System.out.println("Error, no se ha podido insertat el libro" + e);
            return -1;
        }

    }

    public int deleteNode(String titulo) {
        //con este método, borramos un nodo (libro) mediante un título pasado por teclado

        System.out.println("Buscando el libro " + titulo + " para borrarlo.");

        try {
            Node raiz = doc.getDocumentElement();
            NodeList listaNodo = doc.getElementsByTagName("Titulo"); //creamos un NodeList  con todos los títulos 
            Node n1;

            for (int i = 0; i < listaNodo.getLength(); i++) { //recorremos el NodeList de títulos 
                n1 = listaNodo.item(i); //asignamos al nodo el valor de la posición i

                if (n1.getNodeType() == Node.ELEMENT_NODE) { //si es un nodo válido 
                    if (n1.getChildNodes().item(0).getNodeValue().equals(titulo)) { //si el título pasado por parámetro
                        //coincide con el de la posición i
                        System.out.println("Borrando el nodo <Libro> con título :" + titulo);

                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode()); //borramos el nodo
                    }
                }
            }
            System.out.println("Nodo borrado");
            return 0;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }

    }

    void guardarDOMcomoArchivo(String nuevoArchivo) throws TransformerConfigurationException {
        //con este método, guardamos el dom como un archivo xml
        //mediante un String pasado por parámetro que se corresponde con el nombre y extensión de dicho
        //archivo (por ejemplo, "LibrosDeDom.xml").
        //utilizaremos las clases StreamResult y Transformer. 
        try {
            Source src = new DOMSource(doc); // Definimos el origen
            StreamResult rst = new StreamResult(new File(nuevoArchivo)); 

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(src, (javax.xml.transform.Result) rst); //se crea el archivo xml
            System.out.println("Archivo creado del DOM con éxito\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
