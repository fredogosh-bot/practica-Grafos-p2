package algoritmodijkstra;


public class AlgoritmoDijkstra {

    public class GrafoMatriz {

    private int numVerts;
    private int[][] matAd;
    
    public GrafoMatriz(int numVerts) {
        this.numVerts = numVerts;
        this.matAd = new int[numVerts][numVerts]; // Se crea con el tamaño correcto
    }    
   
        
    // para grafos ponderados
    public void nuevoArco(int va, int vb, int peso) {
        va--;
        vb--;
        
        if (va < 0 || vb < 0 || va > numVerts || vb > numVerts) 
            throw new IllegalArgumentException("Índice de vértice fuera de rango.");
        
        matAd[va][vb] = peso;
    }
    

    public boolean adyacente(int va, int vb) {

        if (va < 0 || vb < 0 || va >= numVerts || vb >= numVerts) 
            throw new IllegalArgumentException("Índice de vértice fuera de rango.");
        
        return matAd[va][vb] == 1;
    }

    public void Pvalor(int va, int vb, int v) {

        // Validación de índices (consistente con el método adyacente)
        if (va < 0 || vb < 0 || va >= numVerts || vb >= numVerts) 
            throw new IllegalArgumentException("Índice de vértice fuera de rango.");

        matAd[va][vb] = v;
    }
    
   // --- ALGORITMO DE DIJKSTRA ---
    public void dijkstra(int nodoOrigen) {
        nodoOrigen--; // Ajuste a índice 0-based
        
        int[] distancias = new int[numVerts];
        int[] padres = new int[numVerts];
        boolean[] visitados = new boolean[numVerts];

        // 1. Inicialización
        for (int i = 0; i < numVerts; i++) {
            distancias[i] = Integer.MAX_VALUE; // Infinito (desconocido)
            padres[i] = -1; // Sin padre aún
            visitados[i] = false;
        }
        
        distancias[nodoOrigen] = 0; // La distancia al origen es 0

        // 2. Ciclo principal
        for (int i = 0; i < numVerts; i++) {
            
            // Buscar el nodo con la distancia mínima que NO haya sido visitado
            int u = buscarMinimaDistancia(distancias, visitados);
            
            // Si es -1 o infinito, significa que no hay más nodos alcanzables
            if (u == -1 || distancias[u] == Integer.MAX_VALUE) break;

            visitados[u] = true;

            // Revisar vecinos de 'u'
            for (int v = 0; v < numVerts; v++) {
                // Si hay conexión (matAd[u][v] != 0) Y no está visitado
                if (matAd[u][v] != 0 && !visitados[v]) {
                    
                    int nuevoCosto = distancias[u] + matAd[u][v];
                    
                    // Si encontramos un camino más corto, actualizamos
                    if (nuevoCosto < distancias[v]) {
                        distancias[v] = nuevoCosto;
                        padres[v] = u; // Guardamos que llegamos a 'v' desde 'u'
                    }
                }
            }
        }

        // 3. Imprimir resultados (Requisito del profesor)
        imprimirSolucionDijkstra(nodoOrigen, distancias, padres);
    }

    // Método auxiliar para encontrar el nodo más cercano no visitado
    private int buscarMinimaDistancia(int[] distancias, boolean[] visitados) {
        int min = Integer.MAX_VALUE;
        int indiceMin = -1;

        for (int i = 0; i < numVerts; i++) {
            if (!visitados[i] && distancias[i] <= min) {
                min = distancias[i];
                indiceMin = i;
            }
        }
        return indiceMin;
    }

    // Método auxiliar para mostrar la salida bonita
    private void imprimirSolucionDijkstra(int origen, int[] distancias, int[] padres) {
        System.out.println("\nResultados Dijkstra desde vértice v" + (origen + 1) + ":");
        
        for (int i = 0; i < numVerts; i++) {
            if (i != origen) {
                if (distancias[i] != Integer.MAX_VALUE) {
                    System.out.print("Hacia v" + (i + 1) + ": Costo = " + distancias[i] + ", Camino: ");
                    imprimirCamino(i, padres);
                    System.out.println();
                } else {
                    System.out.println("Hacia v" + (i + 1) + ": No hay camino.");
                }
            }
        }
    }

    // Método recursivo para imprimir el camino hacia atrás y que salga en orden
    private void imprimirCamino(int actual, int[] padres) {
        if (actual == -1) return;
        
        imprimirCamino(padres[actual], padres); // Llamada recursiva (va hacia atrás hasta el origen)
        System.out.print("v" + (actual + 1) + (padres[actual] != -1 ? "-" : "")); // Imprime al volver
        // Nota visual: Esto imprimirá algo tipo v1v3v5. Si quieres guiones exactos, requiere un ajuste menor de formato.
    }

    
}
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
