import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Heap<E> {
    List<E> A = new ArrayList<>();
    int max;
    int n;
    boolean ace;
    Comparator<E> cmp;

    public Heap(int max, boolean ace, Comparator<E> cmp) {
        this.max = max;
        this.ace = ace;
        this.A = new ArrayList<>();
        this.n = 0;
        this.cmp = cmp;
    }

    public int idxIzq(int r) {
        int valor = r * 2 + 1;
        if (valor >= n) return -1;
        return valor;
    }

    public int idxDer(int r) {
        int valor = r * 2 + 2;
        if (valor >= n) return -1;
        return valor;
    }

    public int idxRaiz(int hijo) {
        int valor = (hijo - 1) / 2;
        if (valor < 0) return -1;
        return valor;
    }

    public boolean eshoja(int i) {
        return idxIzq(i) == -1 && idxDer(i) == -1;
    }

    private int mayor(int raiz, int izq, int der) {
        int mayor = raiz;
        
        if (izq != -1 && cmp.compare(A.get(izq), A.get(mayor)) > 0) {
            mayor = izq;
        }
        
        if (der != -1 && cmp.compare(A.get(der), A.get(mayor)) > 0) {
            mayor = der;
        }
        
        return mayor;
    }

    private int menor(int raiz, int izq, int der) {
        int menor = raiz;
        
        if (izq != -1 && cmp.compare(A.get(izq), A.get(menor)) < 0) {
            menor = izq;
        }
        
        if (der != -1 && cmp.compare(A.get(der), A.get(menor)) < 0) {
            menor = der;
        }
        
        return menor;
    }

    private int revisar(int raiz, int izq, int der) {
        if (ace) {
            return mayor(raiz, izq, der);
        } else {
            return menor(raiz, izq, der);
        }
    }

    private void intercambio(int x, int y) {
        E tmp;
        tmp = A.get(x);
        A.set(x, A.get(y));
        A.set(y, tmp);
    }

    public void Ajustar(int idanio) {
        if (eshoja(idanio)) return;
        
        int idx_mayor_o_menor = revisar(idanio, idxIzq(idanio), idxDer(idanio));

        if (idanio == idx_mayor_o_menor) return;

        intercambio(idanio, idx_mayor_o_menor);
        Ajustar(idx_mayor_o_menor);
    }

    public void construirHeap(ArrayList<E> AMalo) {
        this.A = new ArrayList<>(AMalo);
        this.n = A.size();
        
        for (int i = n - 1; i >= 0; i--) {
            Ajustar(i);
        }
    }

    public boolean insertar(E elemento) {
        if (n >= max) {
            return false;
        }
        
        A.add(elemento);
        n++;
        Ajustar(0);
        return true;
    }

    public E extraer() {
        if (n == 0) {
            return null;
        }
        
        E raiz = A.get(0);
        E ultimo = A.get(n - 1);
        A.remove(n - 1);
        n--;
        
        if (n > 0) {
            A.set(0, ultimo);
            Ajustar(0);
        }
        
        return raiz;
    }

    public E obtenerRaiz() {
        if (n == 0) {
            return null;
        }
        return A.get(0);
    }

    public boolean estaVacio() {
        return n == 0;
    }

    public int tamaño() {
        return n;
    }
}
