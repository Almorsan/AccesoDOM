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
        //Con este m�todo abrimos el archivo xml y generamos el DOM mediante un objeto de tipo File
        //que le pasamos por par�metro. Para ello, usamos las clases DocumentBuilder y DocumentBuilder factory

        try {
            System.out.println("Abriendo archivo XML file y generando DOM...");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true); //con esto, se ignoran comentarios
            factory.setIgnoringElementContentWhitespace(true); //con esto, se ignoran espacios en blanco
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            System.out.println("DOM creado con �xito.");

            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }

    }

    public void recorreDOMyMuestra() {
        //Con este m�todo, recorremos el DOM y mostramos el contenido de cada l�nea.
        //Para ello, usamos las clases Node y NodeList
        String[] datos = new String[3]; //en este array guardamos la informaci�n  de cada libro seg�n el �ndice
        Node nodo = null;
        Node raiz = doc.getFirstChild(); //obtenemos el nodo ra�z
        NodeList listaNodos = raiz.getChildNodes(); //obtenemos los hijos del nodo ra�z

        for (int i = 0; i < listaNodos.getLength(); i++) { //recorremos la lista de nodos

            nodo = listaNodos.item(i); //guardamos que hay en la posici�n i de la lista

            if (nodo.getNodeType() == Node.ELEMENT_NODE) { //si el nodo es correcto

                Node ntemp = null;
                int contador = 1;

                datos[0] = nodo.getAttributes().item(0).getNodeValue(); //guardamos el valor del atributo en la posici�n cero
                NodeList nl2 = nodo.getChildNodes();

                for (int j = 0; j < nl2.getLength(); j++) { //en este bucle recorremos todos los nodos de un libro 
                    //en concreto y le asignamos valor a los �ndices del array de String (menos al �ndice 0, que es el atributo

                    ntemp = nl2.item(j); //asignamos a ntem el �tem del �ndice de j

                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) { //si el nodo es correcto

                        datos[contador] = ntemp.getTextContent(); //guardamos el valor del atributo.
                        //en la primera �vuelta�, se  guarda el valor del �ndice 1.
                        contador++;

                    }

                }
                //System.out.println(datos[0]+"--"+datos[2]+"--"+datos[1]);
                System.out.println("T�tulo: " + datos[1] + "\n");
                System.out.println("A�o de publicaci�n: " + datos[0] + "\n");
                System.out.println("Autor: " + datos[2] + "\n");
                System.out.println("--------");
                System.out.println("");

            }

        }

    }

    public int insertarLibroEnDOM(String titulo, String autor, String fecha) {
        //con este m�todo, introducimos un nuevo libro en el DOM mediante una serie de par�metros
        //dados (titulo, autor y fecha). 

        try {
            System.out.println("A�adir libro al �rbol DOM: " + titulo + "; " + autor + "; " + fecha);
            //T�TULO
            Node ntitulo = doc.createElement("T�tulo");
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
            //a�adimos el atributo (fecha de publicaci�n)
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
        //con este m�todo, borramos un nodo (libro) mediante un t�tulo pasado por teclado

        System.out.println("Buscando el libro " + titulo + " para borrarlo.");

        try {
            Node raiz = doc.getDocumentElement();
            NodeList listaNodo = doc.getElementsByTagName("Titulo"); //creamos un NodeList  con todos los t�tulos 
            Node n1;

            for (int i = 0; i < listaNodo.getLength(); i++) { //recorremos el NodeList de t�tulos 
                n1 = listaNodo.item(i); //asignamos al nodo el valor de la posici�n i

                if (n1.getNodeType() == Node.ELEMENT_NODE) { //si es un nodo v�lido 
                    if (n1.getChildNodes().item(0).getNodeValue().equals(titulo)) { //si el t�tulo pasado por par�metro
                        //coincide con el de la posici�n i
                        System.out.println("Borrando el nodo <Libro> con t�tulo :" + titulo);

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
        //con este m�todo, guardamos el dom como un archivo xml
        //mediante un String pasado por par�metro que se corresponde con el nombre y extensi�n de dicho
        //archivo (por ejemplo, "LibrosDeDom.xml").
        //utilizaremos las clases StreamResult y Transformer. 
        try {
            Source src = new DOMSource(doc); // Definimos el origen
            StreamResult rst = new StreamResult(new File(nuevoArchivo)); 

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(src, (javax.xml.transform.Result) rst); //se crea el archivo xml
            System.out.println("Archivo creado del DOM con �xito\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
