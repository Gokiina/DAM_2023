package es.florida;

import java.awt.EventQueue;
import java.util.Comparator;
import java.io.IOException;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

/**
 * Clase para realizar diversas operaciones en un directorio: ver contenido .txt, buscar coincidencias y fusionar ficheros
 * @author Ana Rodríguez
 */
@SuppressWarnings("serial")
public class Principal extends JFrame {

	private JPanel contentPane;
	private JPanel superior;
	private JLabel tDirectorio;
	private JTextField textoDirectorio;
	private JButton botonBuscarDirectorio;
	private JPanel centro;
	private JLabel tFiltros;
	private boolean ordenAscendente = true;
	private JRadioButton seleccionOrden;
	private JTextPane panelPrincipal;
	private JPanel inferiorIzq;
	private JLabel tCoincidencias;
	private JButton botonBuscarCoincidencias;
	private JTextField textoCoincidencias;
	private JTextPane panelCoincidencias;
	private JPanel inferiorDer;
	private JLabel tFusion;
	private JButton botonFusionar;
	private JTextPane panelFicheros;
	private JComboBox<String> comboBox;
    private JScrollPane scrollPane;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private JScrollPane scrollPane_1;
    private JScrollPane scrollPane_2;

    
	/**
	 * Método principal para iniciar la aplicación
	 * @param args no utilizados
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Constructor de la clase Principal. Inicializa la interfaz de usuario.
	 * Contiene los componentes de la aplicación y configura la pantalla principal
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		superior = new JPanel();
		superior.setLayout(null);
		
		tDirectorio = new JLabel("Escribe un directorio:");
		tDirectorio.setBounds(80, 15, 274, 26);
		superior.add(tDirectorio);
		
		textoDirectorio = new JTextField();
		textoDirectorio.setBounds(80, 50, 526, 26);
		superior.add(textoDirectorio);
		textoDirectorio.setColumns(10);
		
		botonBuscarDirectorio = new JButton("Buscar");
		botonBuscarDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ft_buscarDirectorio(textoDirectorio.getText());
			}
		});
		botonBuscarDirectorio.setBounds(638, 30, 117, 29);
		superior.add(botonBuscarDirectorio);
		
		centro = new JPanel();
		centro.setLayout(null);
		
		tFiltros = new JLabel("Filtros:");
		tFiltros.setBounds(600, 10, 129, 16);
		centro.add(tFiltros);
		
		seleccionOrden = new JRadioButton("Orden ascendente", true);
		seleccionOrden.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ordenAscendente = seleccionOrden.isSelected();
		        ft_ordenarArchivos(textoDirectorio.getText());
		    }
		});
		
		seleccionOrden.setBounds(600, 40, 165, 23);
		centro.add(seleccionOrden);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 15, 511, 176);
		centro.add(scrollPane);
		
		panelPrincipal = new JTextPane();
		scrollPane.setViewportView(panelPrincipal);
		
		inferiorIzq = new JPanel();
		inferiorIzq.setLayout(null);
		
		tCoincidencias = new JLabel("Busqueda de coincidencias:");
		tCoincidencias.setBounds(80, 15, 174, 16);
		inferiorIzq.add(tCoincidencias);
		
		botonBuscarCoincidencias = new JButton("Buscar");
		botonBuscarCoincidencias.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	try {
					ft_buscarCoincidencias(textoDirectorio.getText(), textoCoincidencias.getText());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		botonBuscarCoincidencias.setBounds(165, 120, 117, 29);
		inferiorIzq.add(botonBuscarCoincidencias);
		
		textoCoincidencias = new JTextField();
		textoCoincidencias.setBounds(48, 30, 360, 26);
		textoCoincidencias.setColumns(10);
		inferiorIzq.add(textoCoincidencias);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(50, 60, 360, 60);
		inferiorIzq.add(scrollPane_1);
		
		panelCoincidencias = new JTextPane();
		scrollPane_1.setViewportView(panelCoincidencias);
		
		inferiorDer = new JPanel();
		inferiorDer.setLayout(null);
		
		tFusion = new JLabel("Fusionar ficheros:");
		tFusion.setBounds(30, 15, 274, 16);
		inferiorDer.add(tFusion);
		
		botonFusionar = new JButton("Fusionar");
		botonFusionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ft_fusionarFicheros();
			}
		});
		botonFusionar.setBounds(163, 10, 117, 29);
		inferiorDer.add(botonFusionar);
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, superior, -6, SpringLayout.NORTH, centro);
		sl_contentPane.putConstraint(SpringLayout.NORTH, inferiorDer, 315, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, inferiorDer, 472, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, inferiorDer, 479, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, inferiorDer, 792, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, inferiorIzq, 315, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, inferiorIzq, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, inferiorIzq, 479, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, inferiorIzq, 467, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, centro, 105, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, centro, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, centro, 310, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, centro, 792, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, superior, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, superior, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, superior, 792, SpringLayout.WEST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(superior);
		contentPane.add(centro);
		
		comboBox = new JComboBox<String>();
        comboBox.setBounds(600, 70, 165, 27);
        centro.add(comboBox);
        contentPane.add(inferiorIzq);
        contentPane.add(inferiorDer);
        
        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(30, 43, 250, 102);
        inferiorDer.add(scrollPane_2);
        
        panelFicheros = new JTextPane();
        scrollPane_2.setViewportView(panelFicheros);
        comboBox.addItem("Nombre");
        comboBox.addItem("Tamaño");
        comboBox.addItem("Última modificación");
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ft_ordenarDirectorio(textoDirectorio.getText(), comboBox.getSelectedItem().toString());
            }
        });


	}
	
	/**
	 * Actualiza el panel de visualización con la lista de archivos que contiene el directorio
	 * @param archivos String de archivos del directorio
	 */
	public void ft_actualizarPanel(File[] archivos) {
		panelPrincipal.setText("");

        for (int i = 0; i < archivos.length; i++) {
            File archivo = archivos[i];

            String nombre = archivo.getName();
            String extension = nombre.substring(nombre.lastIndexOf(".") + 1);
            long tamanyo = archivo.length();
            long ultimaModificacion = archivo.lastModified();
            String fechaFormateada = dateFormat.format(new Date(ultimaModificacion));

            panelPrincipal.setText(panelPrincipal.getText() +
                    "Nombre: " + nombre + "\n" +
                    "Extensión: " + extension + "\n" +
                    "Tamaño: " + tamanyo + " bytes\n" +
                    "Última Modificación: " + fechaFormateada + "\n\n");
        }
	}
	
	/**
	 * Busca el directorio y muestra la lista de archivos en el panel principal
	 * @param directorio Directorio a buscar
	 */
	public void ft_buscarDirectorio(String directorio) {
	    File directorioFile = new File(directorio);

	    if (directorioFile.exists()) {
	        File[] archivos = directorioFile.listFiles(new FiltroExtension(".txt"));

	        Arrays.sort(archivos, new Comparator<File>() {
	            @Override
	            public int compare(File file1, File file2) {
	                return file1.getName().compareTo(file2.getName());
	            }
	        });
	        ft_actualizarPanel(archivos);
	    } else 
	        panelPrincipal.setText("Directorio no válido.");
	}
	
	/**
	 *  Ordena los archivos en orden ascenciente o descendiente
	 * @param directorio Directorio a ordenar
	 */
	public void ft_ordenarArchivos(String directorio) {
	    File directorioFile = new File(directorio);

	    if (directorioFile.exists()) {
	        File[] archivos = directorioFile.listFiles(new FiltroExtension(".txt"));

	        if (ordenAscendente) {
	        	Arrays.sort(archivos, new Comparator<File>() {
		            @Override
		            public int compare(File file1, File file2) {
		                return file1.getName().compareTo(file2.getName());
		            }
		        });
	        } else {
	        	Arrays.sort(archivos, new Comparator<File>() {
		            @Override
		            public int compare(File file1, File file2) {
		                return file2.getName().compareTo(file1.getName());
		            }
		        });
	        }
	        ft_actualizarPanel(archivos);
	    } else
	        panelPrincipal.setText("Directorio no válido.");
	}

	/**
	 * Ordena los archivos en el directorio y actualiza el panel principal según el criterio especificado
	 * @param directorio Directorio a ordenar
	 * @param criterioOrden Criterio de ordenación: Nombre, tamaño o última modificación
	 */
	public void ft_ordenarDirectorio(String directorio, String criterioOrden) {
	    File directorioFile = new File(directorio);

	    if (directorioFile.exists()) {
	        File[] archivos = directorioFile.listFiles(new FiltroExtension(".txt"));

	        Comparator<File> comparador = null;

	        if ("Nombre".equals(criterioOrden))
	            comparador = Comparator.comparing(File::getName);
	        else if ("Tamaño".equals(criterioOrden))
	            comparador = Comparator.comparingLong(File::length);
	        else if ("Última Modificación".equals(criterioOrden))
	            comparador = Comparator.comparingLong(File::lastModified);

	        if (comparador != null) {
	            if (!ordenAscendente)
	                comparador = comparador.reversed();
	            Arrays.sort(archivos, comparador);
	        }
	        ft_actualizarPanel(archivos);
	    } else
	        panelPrincipal.setText("Directorio no válido.");
	}

	/**
	 *  Busca coincidencias de texto en los archivos del directorio y muestra los resultados
	 * @param directorio Directorio donde se buscan las coincidencias
	 * @param textoCoincidencias Texto a buscar dentro de los archivos
	 * @throws BadLocationException Excepción de error al buscar coincidencias
	 */
	public void ft_buscarCoincidencias(String directorio, String textoCoincidencias) throws BadLocationException {
		File directorioFile = new File(directorio);

	    if (directorioFile.exists()) {
	        File[] archivos = directorioFile.listFiles(new FiltroExtension(".txt"));

	        panelCoincidencias.setText("");

	        for (File archivo : archivos) {
	            String nombreArchivo = archivo.getName();
	            StringBuilder resultado = new StringBuilder();
	            int coincidencias = 0;

	            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
	                String linea;
	                while ((linea = reader.readLine()) != null) {
	                    int indice = linea.indexOf(textoCoincidencias);
	                    while (indice != -1) {
	                        coincidencias++;
	                        indice = linea.indexOf(textoCoincidencias, indice + 1);
	                    }
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	            resultado.append(nombreArchivo).append(" -> ").append(coincidencias).append(" coincidencias\n");

	            StyledDocument doc = panelCoincidencias.getStyledDocument();
	            SimpleAttributeSet keyWord = new SimpleAttributeSet();
	            StyleConstants.setBold(keyWord, true);
	            doc.insertString(doc.getLength(), resultado.toString(), keyWord);
	        }
	    } else
	        panelCoincidencias.setText("Directorio no válido.");
	}

	
	/**
	 * Fusiona los archivos seleccionados en un nuevo archivo
	 */
	public void ft_fusionarFicheros(){
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File[] archivosSeleccionados = fileChooser.getSelectedFiles();
            String nombreNuevoArchivo = JOptionPane.showInputDialog("Ingrese el nombre del nuevo archivo:");

            File directorio = fileChooser.getCurrentDirectory();
            File nuevoArchivo = new File(directorio, nombreNuevoArchivo);

            if (nuevoArchivo.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(null, "El archivo ya existe. ¿Desea sobrescribirlo?", "Confirmar sobreescritura", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.NO_OPTION)
                    return;
            }

            try (FileOutputStream fusionOutputStream = new FileOutputStream(nuevoArchivo)) {
                for (File archivo : archivosSeleccionados) {
                    Files.copy(archivo.toPath(), fusionOutputStream);
                }
                JOptionPane.showMessageDialog(null, "Archivos fusionados con éxito.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al fusionar archivos.");
            }
        }
	}

}
	
	class FiltroExtension implements FilenameFilter {
		
		String extension;
		
		FiltroExtension(String extension) {
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(extension);
		}
}
