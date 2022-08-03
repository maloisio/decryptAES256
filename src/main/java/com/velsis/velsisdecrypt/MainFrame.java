package com.velsis.velsisdecrypt;


import com.velsis.velsisdecrypt.cryptography.Decrypt;

import static java.awt.Color.GREEN;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.*;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author msmaniotto
 */
public class MainFrame extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form MainFrame
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MainFrame.class);
    public static JButton buttonSearch, buttonExit, buttonSave, buttonStart, buttonStop;
    JFileChooser chooser = new JFileChooser();
    JPanel panel = new JPanel();
    JTextField jtSearch, jtSave;
    JLabel lSearch, lSave;
    public static JProgressBar progressBarStart;
    ImageIcon imageBack;
    JFrame frame;
    
    String caminhoSave;
    String caminhoEncript;
    
    boolean finishedThread = true;
    public static boolean threadSleep = false;
    public static Object lock1 = new Object();
    
    public MainFrame() {
        
        //initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Velsis Decrypt");
        //this.setLocation(500, 300);
        //this.setLayout(new FlowLayout());
        this.setResizable(false);
        
        
        panel.setPreferredSize(new Dimension(470, 300));
        panel.setLayout(null);
        
        add(panel);
        pack();
        setLocationRelativeTo(null);
 
        
        lSearch = new JLabel("Selecione o diretório contendo os arquivos criptografados: ");
        lSave = new JLabel("Selecione um diretório para salvar os arquivos descriptogrados: ");
        
        buttonSearch = new JButton("Procurar");
        buttonSave = new JButton("Procurar");
        buttonStart = new JButton("Iniciar");
        buttonExit = new JButton("Sair");
        buttonStop = new JButton("Parar");
        
        jtSearch = new JTextField(300);
        jtSave = new JTextField(300);
        
        progressBarStart = new JProgressBar();
        
        buttonExit.addActionListener(this);
        buttonSearch.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonStart.addActionListener(this);
        buttonStop.addActionListener(this);
   
        frame = new JFrame();
        
        lSearch.setBounds(10, 30, 500, 20);
        panel.add(lSearch);
        buttonSearch.setBounds(350, 50, 100, 25);
        panel.add(buttonSearch);
        jtSearch.setBounds(10, 50, 325, 25);
        jtSearch.setEditable (false);
        panel.add(jtSearch);
        
        
        lSave.setBounds(10, 80, 500, 20);
        panel.add(lSave);
        buttonSave.setBounds(350, 100, 100, 25);
        panel.add(buttonSave);
        jtSave.setBounds(10, 100, 325, 25);
        jtSave.setEditable (false);
        panel.add(jtSave);
        
        progressBarStart.setBounds(50, 200, 370, 50);
        progressBarStart.setStringPainted(true);
        progressBarStart.setForeground(GREEN);
        panel.add(progressBarStart);
        
        buttonStart.setBounds(100, 150, 100, 25);
        panel.add(buttonStart);
        
        buttonStop.setBounds(270, 150, 100, 25);
        panel.add(buttonStop);
     
        buttonExit.setBounds(350, 270, 100, 25);
        panel.add(buttonExit);
        
        setVisible(true);
        //this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==buttonSearch) {
            
            if(finishedThread == true){
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //setar se quer arquivo ou pasta
                chooser.setAcceptAllFileFilterUsed(false); //desabilitando "todos os arquivos"

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //se selecionado o botao abrir
                    System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

                    jtSearch.setText(chooser.getSelectedFile().getPath());
                    caminhoEncript = chooser.getSelectedFile().getPath();
                } else {
                    System.out.println("No Selection ");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Aguarde o processo de descriptografia atual finalizar!");
            }
        }
        
        if(e.getSource()==buttonExit) {
            System.exit(0);
        }
        
        if(e.getSource()==buttonSave) {
            if(finishedThread == true){
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //setar se quer arquivo ou pasta
                chooser.setAcceptAllFileFilterUsed(false); //desabilitando "todos os arquivos"

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //se selecionado o botao abrir
                    System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

                    jtSave.setText(chooser.getSelectedFile().getPath());
                    caminhoSave = chooser.getSelectedFile().getPath();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Aguarde o processo de descriptografia atual finalizar!");
            }   
        }
        
        if(e.getSource()==buttonStart) {
            threadSleep = false;
                if (finishedThread == true) { //se a thread atual de criptografia nao finalizou, nao vai ser possivel iniciar outra
                    new Thread(t1).start(); //thread que inicia decriptografia
                } else {
                    JOptionPane.showMessageDialog(null, "Aguarde o processo de descriptografia atual finalizar!");
                }            
        }
        
        if(e.getSource()==buttonStop) {
            if(threadSleep == false){
                threadSleep = true;
            }
        }
    }
    
    public Runnable t1 = new Runnable(){
        public void run(){
            finishedThread = false;
      
            if(caminhoSave == null || caminhoEncript == null){
                logger.info("Um ou mais campos vazios");
                JOptionPane.showMessageDialog(null, "Um ou mais campos vazios.");
                
            }else{
                    //String resp = chooser.getSelectedFile().getName();
                    int nTotalFiles = 0;
                
                        try {
                            nTotalFiles = Decrypt.initDecrypt(caminhoEncript, caminhoSave); //enviar o caminhos das pastas selecionadas para run
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
            
                    if(nTotalFiles == 0){
                        logger.info("Não há arquivos com criptografia compatíveis no diretório");
                        JOptionPane.showMessageDialog(null, "Não há arquivos com criptografia compatíveis no diretório");
                        progressBarStart.setValue(0);
                    }else{
                        logger.info("Total de " + nTotalFiles + " arquivos descriptografados!");
                        JOptionPane.showMessageDialog(null, "Total de " + nTotalFiles + " arquivos descriptografados!");
                        progressBarStart.setValue(0);
                    }
            }
            finishedThread = true; //indicador que a thread finalizou
            System.out.println("Thread " + Thread.currentThread().getName() + " finalizada");
        }
    };
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        new MainFrame();     
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
