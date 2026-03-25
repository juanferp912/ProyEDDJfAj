import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class APrefijo<E> {
    Map<Character, APrefijo> hijos;
    boolean finNombre;
    LinkedList<E> datos;
    
    public APrefijo() {
        hijos = new HashMap<>();
        finNombre = false;
        datos = new LinkedList<>();
    }
    
    public void insertar(String palabra, E dato) {
        APrefijo<E> nodo = this;
        
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            
            if (!nodo.hijos.containsKey(letra)) {
                nodo.hijos.put(letra, new APrefijo<E>());
            }
            
            nodo = (APrefijo<E>) nodo.hijos.get(letra);
        }
        
        nodo.finNombre = true;
        nodo.datos.add(dato);
    }
    
    public LinkedList<E> buscar(String palabra) {
        APrefijo<E> nodo = this;
        
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            
            APrefijo<E> nodoSiguiente = (APrefijo<E>) nodo.hijos.get(letra);
            
            if (nodoSiguiente == null) {
                return new LinkedList<>();
            }
            
            nodo = nodoSiguiente;
        }
        
        if (nodo.finNombre) {
            return nodo.datos;
        } else {
            return new LinkedList<>();
        }
    }
    
    public LinkedList<String> buscarPrefijo(String prefijo) {
        APrefijo<E> nodo = this;
        LinkedList<String> resultado = new LinkedList<>();
        for (int i = 0; i < prefijo.length(); i++) {
            char letra = prefijo.charAt(i);
            
            if (!nodo.hijos.containsKey(letra)) {
                return resultado; 
            }   
            nodo = (APrefijo<E>) nodo.hijos.get(letra);
        }
        recopilarPalabras(nodo, prefijo, resultado);
        return resultado;
    }
    
    private void recopilarPalabras(APrefijo<E> nodo, String palabraActual, LinkedList<String> resultado) {
        if (nodo.finNombre) {
            resultado.add(palabraActual);
        }
        
        for (Map.Entry<Character, APrefijo> entrada : nodo.hijos.entrySet()) {
            char letra = entrada.getKey();
            APrefijo<E> hijo = entrada.getValue();
            recopilarPalabras(hijo, palabraActual + letra, resultado);
        }
    }
}
