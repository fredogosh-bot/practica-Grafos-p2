package algoritmodijkstra;


public class AlgoritmoDijkstra {
    
    public static class GrafoMatriz {

    private int numVerts;
    private int[][] matAd;
    
    public GrafoMatriz(int numVerts) {
        this.numVerts = numVerts;
        this.matAd = new int[numVerts][numVerts]; // Se crea la matriz con el tamaño necesario
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
        
        return matAd[va][vb] != 0;
    }

   
    
   // --- ALGORITMO DE DIJKSTRA ---
    public void dijkstra(int nodoOrigen) {
        nodoOrigen--; // se ajusta el nodo origen
        
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
            int u = buscarDistanciaMinima(distancias, visitados);
            
            // Si es -1 o infinito, significa que no hay más nodos alcanzables
            if (u == -1 || distancias[u] == Integer.MAX_VALUE) break;

            visitados[u] = true;

            // Revisar vecinos de 'u'
            for (int v = 0; v < numVerts; v++) {
                // Si hay conexión (matAd[u][v] != 0) Y no está visitado
                if (adyacente(u,v) && !visitados[v]) {
                    
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
    private int buscarDistanciaMinima(int[] distancias, boolean[] visitados) {
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
        int numeroDeNodos = 6;
        GrafoMatriz g = new GrafoMatriz(numeroDeNodos);
        

        System.out.println("--- Creando Grafo con Pesos ---");
        // Ejemplo similar al que pide el profe
        // v1 -> v2 (peso 5)
        // v1 -> v3 (peso 10)
        // v2 -> v4 (peso 15)
        // v3 -> v5 (peso 20)
        // etc...
        
        // Conectamos v1 (1) con v6 (6) indirectamente para ver si halla el camino
        g.nuevoArco(1, 2, 2); // De 1 a 2 cuesta 2
        g.nuevoArco(1, 3, 4); // De 1 a 3 cuesta 4
        g.nuevoArco(2, 4, 7); 
        g.nuevoArco(3, 5, 3);
        g.nuevoArco(4, 6, 1);
        g.nuevoArco(5, 6, 5);
        g.nuevoArco(2, 3, 1); // Camino cruzado
        
        // Ejecutamos Dijkstra desde el nodo 1
        g.dijkstra(1);
    }
    
    

    
}
