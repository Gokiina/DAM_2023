package florida.AE1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * La clase Manufacture implementa la interfaz Runnable, 
 * se encarga de fabricar las piezas en hilos independientes
 */
public class Manufacture implements Runnable {
    private LinkedList<String> piezasQueue;
    private List<String> piezasFabricadas;
    private CountDownLatch latch;
    private static final int MAX_MACHINES = 8;
    private Semaphore machines = new Semaphore(MAX_MACHINES);
    private PrintStream out = System.out;
    
    /**
     * Establece el CountDownLatch para coordinar la fabricación
     * @param latch El CountDownLatch a establecer
     */
    public void setCountDownLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * Constructor de Manufacture
     * @param piezasQueue Cola de piezas a fabricar
     * @param fileName Nombre del archivo para la salida
     */
    public Manufacture(LinkedList<String> piezasQueue, String fileName) {
        this.piezasQueue = piezasQueue;
        this.piezasFabricadas = new ArrayList<>();
    }
    /**
     * Establece la salida para los mensajes
     * @param out La salida a establecer
     */
    public void setOutput(PrintStream out) {
        this.out = out;
    }

    /**
     * Simula el proceso de fabricacion de una pieza
     * @param tiempoFabricacion El tiempo de fabricación en milisegundos
     */
    public void procesoFabricacion(int tiempoFabricacion) {
        try {
            Thread.sleep(tiempoFabricacion);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     *Implementación del método run de la interfaz Runneable
     *Inicia la fabricación continua de piezas
     */
    @Override
    public void run() {
        while (true) {
            try {
                fabricarPieza();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Fabrica una pieza, la añade a la lista y gestiona las operaciones asociadas
     * @throws InterruptedException Si la espera es interrumpida
     */
    public void fabricarPieza() throws InterruptedException {
    	String pieza;
    	synchronized (piezasQueue) {
            while (piezasQueue.isEmpty()) {
                piezasQueue.wait();
            }
            pieza = piezasQueue.removeFirst();
        }
        machines.acquire();

        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String piezaFabricada = pieza + "_" + timestamp;

            synchronized (piezasFabricadas) {
                piezasFabricadas.add(piezaFabricada);
            }

            System.out.println("Pieza " + piezaFabricada + " fabricada");

            if (pieza.equals("I")) {
                procesoFabricacion(1000);
            } else if (pieza.equals("O")) {
                procesoFabricacion(2000);
            } else if (pieza.equals("T")) {
                procesoFabricacion(3000);
            } else if (pieza.equals("J") || pieza.equals("L")) {
                procesoFabricacion(4000);
            } else if (pieza.equals("S") || pieza.equals("Z")) {
                procesoFabricacion(5000);
            }
        } finally {
            machines.release();
        }

        synchronized (piezasQueue) {
            piezasQueue.notify();
        }

        if (latch != null) {
            latch.countDown();
        }
    }
    
    /**
     * Escribe el registro de piezas fabricadas en un archivo
     */
    public void escribirRegistroEnArchivo() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "LOG_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (String pieza : piezasFabricadas) {
                writer.write(pieza + "\n");
            }
            out.println("Cantidad de piezas fabricadas: " + piezasFabricadas.size());
            out.println("Registro de piezas escrito en el archivo " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
