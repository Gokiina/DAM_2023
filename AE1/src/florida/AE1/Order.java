package florida.AE1;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

/**
 * La clase order representa la aplicación con interfaz gráfica para gestionar la fabricación de piezas
 */
public class Order extends JFrame {
	private JPanel contentPane;
	private JTextField cantidadI;
	private JTextField cantidadO;
	private JTextField cantidadJ;
	private JTextField cantidadL;
	private JTextField cantidadS;
	private JTextField cantidadZ;
	private JTextField cantidadT;
	public LinkedList<String> piezasQueue = new LinkedList<>();
	private Manufacture manufactureThread;
	private JTextField archivoTextField;
	private JRadioButton archivoRadioButton;
	
	/**
	 * Método principal que inicia la aplicación
	 * @param args Los argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                Order frame = new Order();
	                Manufacture manufacture = new Manufacture(frame.piezasQueue, "");
	                frame.setManufactureThread(manufacture);
	                frame.setVisible(true);
	                new Thread(manufacture).start();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
    /**
     * Establece el hilo de Manufacture asociado a esta instancia de Order
     * @param manufactureThread El hilo de Manufacture
     */
    public void setManufactureThread(Manufacture manufactureThread) {
        this.manufactureThread = manufactureThread;
    }

	/**
	 * Constructor de la clase Order
	 * Inicializa y configura todos los elementos de la interfaz gráfica
	 */
	public Order() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 530);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titulo = new JLabel("Escribe cuantas unidades quieres fabricar de cada tipo:");
		titulo.setBounds(18, 6, 405, 16);
		contentPane.add(titulo);
		
		JLabel I = new JLabel("");
		I.setIcon(new ImageIcon(Order.class.getResource("/media/I.png")));
		I.setBounds(30, 64, 120, 60);
		contentPane.add(I);
		
		cantidadI = new JTextField();
		cantidadI.setText("0");
		cantidadI.setBackground(Color.LIGHT_GRAY);
		cantidadI.setBounds(165, 82, 75, 26);
		contentPane.add(cantidadI);
		cantidadI.setColumns(10);
		
		JLabel O = new JLabel("");
		O.setIcon(new ImageIcon(Order.class.getResource("/media/O.png")));
		O.setBounds(59, 166, 60, 60);
		contentPane.add(O);
		
		cantidadO = new JTextField();
		cantidadO.setText("0");
		cantidadO.setToolTipText("");
		cantidadO.setBackground(Color.LIGHT_GRAY);
		cantidadO.setColumns(10);
		cantidadO.setBounds(165, 182, 75, 26);
		contentPane.add(cantidadO);
		
		JLabel T = new JLabel("");
		T.setIcon(new ImageIcon(Order.class.getResource("/media/T.png")));
		T.setBounds(300, 321, 120, 60);
		contentPane.add(T);
		
		cantidadT = new JTextField();
		cantidadT.setText("0");
		cantidadT.setBackground(Color.LIGHT_GRAY);
		cantidadT.setColumns(10);
		cantidadT.setBounds(435, 338, 75, 26);
		contentPane.add(cantidadT);
		
		JLabel J = new JLabel("");
		J.setIcon(new ImageIcon(Order.class.getResource("/media/J.png")));
		J.setBounds(48, 261, 75, 120);
		contentPane.add(J);
		
		cantidadJ = new JTextField();
		cantidadJ.setText("0");
		cantidadJ.setBackground(Color.LIGHT_GRAY);
		cantidadJ.setColumns(10);
		cantidadJ.setBounds(165, 311, 75, 26);
		contentPane.add(cantidadJ);
		
		JLabel L = new JLabel("");
		L.setIcon(new ImageIcon(Order.class.getResource("/media/L.png")));
		L.setBounds(331, 34, 75, 120);
		contentPane.add(L);
		
		cantidadL = new JTextField();
		cantidadL.setText("0");
		cantidadL.setBackground(Color.LIGHT_GRAY);
		cantidadL.setColumns(10);
		cantidadL.setBounds(435, 82, 75, 26);
		contentPane.add(cantidadL);
		
		JLabel S = new JLabel("");
		S.setIcon(new ImageIcon(Order.class.getResource("/media/S.png")));
		S.setBounds(300, 166, 120, 60);
		contentPane.add(S);
		
		cantidadS = new JTextField();
		cantidadS.setText("0");
		cantidadS.setBackground(Color.LIGHT_GRAY);
		cantidadS.setColumns(10);
		cantidadS.setBounds(432, 182, 75, 26);
		contentPane.add(cantidadS);
		
		JLabel Z = new JLabel("");
		Z.setIcon(new ImageIcon(Order.class.getResource("/media/Z.png")));
		Z.setBounds(300, 241, 120, 60);
		contentPane.add(Z);
		
		cantidadZ = new JTextField();
		cantidadZ.setText("0");
		cantidadZ.setBackground(Color.LIGHT_GRAY);
		cantidadZ.setColumns(10);
		cantidadZ.setBounds(435, 255, 75, 26);
		contentPane.add(cantidadZ);
		
		JButton botonConstuir = new JButton("Construir");
		botonConstuir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	ft_construirPiezas();
			}
		});
		botonConstuir.setBackground(Color.LIGHT_GRAY);
		botonConstuir.setBounds(406, 429, 117, 29);
		contentPane.add(botonConstuir);
		
		JRadioButton consolaRadioButton = new JRadioButton("Mostrar en consola");
		consolaRadioButton.setSelected(true);
		consolaRadioButton.setBounds(40, 418, 168, 23);
		contentPane.add(consolaRadioButton);
		
		archivoRadioButton = new JRadioButton("Escribir en archivo");
		archivoRadioButton.setBounds(40, 453, 168, 23);
		contentPane.add(archivoRadioButton);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(consolaRadioButton);
	    group.add(archivoRadioButton);
		
		archivoTextField = new JTextField();
		archivoTextField.setBounds(220, 452, 130, 26);
		contentPane.add(archivoTextField);
		archivoTextField.setColumns(10);	
	}
	
	/**
	 * Construye las piezas según la cantidad especificada en la interfaz gráfica
	 */
	public void ft_construirPiezas() {
        agregarPiezasALaCola("I", Integer.parseInt(cantidadI.getText()));
        agregarPiezasALaCola("O", Integer.parseInt(cantidadO.getText()));
        agregarPiezasALaCola("T", Integer.parseInt(cantidadT.getText()));
        agregarPiezasALaCola("J", Integer.parseInt(cantidadJ.getText()));
        agregarPiezasALaCola("L", Integer.parseInt(cantidadL.getText()));
        agregarPiezasALaCola("S", Integer.parseInt(cantidadS.getText()));
        agregarPiezasALaCola("Z", Integer.parseInt(cantidadZ.getText()));

        CountDownLatch latch = new CountDownLatch(piezasQueue.size());
        manufactureThread.setCountDownLatch(latch);

        new Thread(() -> {
            synchronized (piezasQueue) {
                piezasQueue.notifyAll();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean escribirEnArchivo = archivoRadioButton.isSelected();
        if (escribirEnArchivo) {
            String nombreArchivo = archivoTextField.getText() + ".txt";
            try {
                PrintStream fileOut = new PrintStream(new FileOutputStream(nombreArchivo));
                manufactureThread.setOutput(fileOut);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error al abrir el archivo: " + e.getMessage());
            }
        } else {
            manufactureThread.setOutput(System.out);
        }
        manufactureThread.escribirRegistroEnArchivo();
    }
	
	/**
	 * Agrega la piezas a la cola de fabricación
	 * @param tipoPieza El tipo de pieza
	 * @param cantidad La cantidad de piezas a agregar
	 */
	public void agregarPiezasALaCola(String tipoPieza, int cantidad) {
	    for (int i = 0; i < cantidad; i++) {
	        synchronized (piezasQueue) {
	            while (piezasQueue.size() >= 8) {
	                try {
	                    piezasQueue.wait();
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	            piezasQueue.offer(tipoPieza);
	            piezasQueue.notifyAll();
	        }
	    }
	}
}