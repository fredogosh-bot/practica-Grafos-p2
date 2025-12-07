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
            throw new IllegalArgumentException("Índice de vertice fuera de rango.");
        
        matAd[va][vb] = peso;
    }
    

    public boolean adyacente(int va, int vb) {

        if (va < 0 || vb < 0 || va >= numVerts || vb >= numVerts) 
            throw new IllegalArgumentException("Índice de vértice fuera de rango.");
        
        return matAd[va][vb] != 0;
    }

   
    
   // --- ALGORITMO DE DIJKSTRA ---
    public void dijkstra(int nodoOrigen, int nodoDestino) {
        nodoOrigen--; // se ajusta el nodo origen
        nodoDestino--;
        int[] distancias = new int[numVerts];
        int[] padres = new int[numVerts];
        boolean[] visitados = new boolean[numVerts];

        // 1. Inicialización
        for (int i = 0; i < numVerts; i++) {
            distancias[i] = Integer.MAX_VALUE; // Se crea un numero ridiculamentes grande para reflejar infinito
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
        imprimirSolucionDijkstra(nodoOrigen, nodoDestino, distancias, padres);
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
    private void imprimirSolucionDijkstra(int origen,int destino, int[] distancias, int[] padres) {
        int indiceDestino = destino;

        // Verificamos si el índice es válido para evitar errores
        if (indiceDestino < 0 || indiceDestino >= numVerts) {
            System.out.println("El vértice destino no existe.");
            return;
        }
        
        System.out.println("Resultados Dijkstra desde vértice v" + (origen + 1) + " hacia vertice v" + (destino+1));

        //Verificamos si hay camino (Si la distancia NO es infinita)
        if (distancias[indiceDestino] != Integer.MAX_VALUE) {
            // Imprimimos el formato exacto que pidió tu profesor
            System.out.println("El camino mínimo de v" + (origen + 1) + " a v" + destino + " es " + distancias[indiceDestino]);
            System.out.print("La secuencia de vértices es: ");
            
            // método recursivo para imprimir la ruta
            imprimirCamino(indiceDestino, padres);
            System.out.println(); 
            
        } else {
            System.out.println("No hay camino posible entre v" + (origen + 1) + " y v" + destino);
        }
    }

    // Método recursivo para imprimir el camino hacia atrás y que salga en orden
    private void imprimirCamino(int actual, int[] padres) {
        if (actual == -1) return;
        
        imprimirCamino(padres[actual], padres); // Llamada recursiva (va hacia atrás hasta el origen)
        System.out.print("v" + (actual + 1) + (padres[actual] != -1 ? "," : "")); // Imprime al volver
        
    }

    
}
    
    public static void main(String[] args) {
        int numeroDeNodos = 6;
        GrafoMatriz g1 = new GrafoMatriz(numeroDeNodos);
        
        
        // se crean los tres grafos dirigidos y ponderados
        //grafo1:
        g1.nuevoArco(1, 2, 10); 
        g1.nuevoArco(1, 5, 100); 
        g1.nuevoArco(1, 4, 30); 
        g1.nuevoArco(2, 3, 50);
        g1.nuevoArco(3, 5, 10);
        g1.nuevoArco(4, 3, 20);
        g1.nuevoArco(4, 5, 60);
        
        //grafo 2:
        
        
        
        g1.dijkstra(1,5);
    }
    
    

    
}
