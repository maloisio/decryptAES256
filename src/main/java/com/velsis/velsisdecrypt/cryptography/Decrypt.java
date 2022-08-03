/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.velsis.velsisdecrypt.cryptography;

import com.velsis.velsisdecrypt.MainFrame;
import static com.velsis.velsisdecrypt.cryptography.Aes256.decodeFileToBase64Binary;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import java.lang.*;

/**
 *
 * @author msmaniotto
 */
public class Decrypt{
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Decrypt.class);
    private static int totalNoDecriptedFile, totalDecriptedFile;
    private static final String[] originalString = new String[1000];
    private static final String[] decryptedString = new String[1000];
    private static File[] listOfFiles;
    private static File dir;
    
    public static void main(String args) throws FileNotFoundException, IOException{
        
    }

    public static int initDecrypt(String caminhoEncript, String caminhoSave) throws FileNotFoundException, IOException, InterruptedException{

        logger.info("Diretório de arquivos encriptados " + caminhoEncript + " selecionado.");
        logger.info("Diretório destino " + caminhoSave + " selecionado.");
        dir = new File(caminhoEncript + "/");
        listOfFiles = dir.listFiles();
        totalNoDecriptedFile = 0;
        totalDecriptedFile = 0;
        for(int i = 0; i < listOfFiles.length; i++){  
            if (listOfFiles[i].isFile()){//se for arquivo (quando nao é pasta [folder])

                logger.info("Arquivo " + listOfFiles[i] + " selecionado para descriptografia.");
                
                    File f = new File(caminhoEncript + "/" + listOfFiles[i].getName()); 
                
                    originalString[i] = Aes256.encodeFileToBase64Binary(f); //converte arquivo (bytes) em string

                    decryptedString[i] = Aes256.decrypt(originalString[i]); //decripta string

                    if(decryptedString[i] != null){
                        //cria arquivo (bytes) a partir da string encriptada
                        byte[] decode = decodeFileToBase64Binary(decryptedString[i]);
                        FileOutputStream outFile = new FileOutputStream(caminhoSave + "/" + listOfFiles[i].getName()); //new FileOutputStream("/home/msmaniotto/teste123/decript/"+listOfFiles[i].getName());
                        outFile.write(decode);//escreve no arquivo

                        totalDecriptedFile++;
                        logger.info("Arquivo " + listOfFiles[i] + " descriptografado.");
                    }else{
                       logger.error("[ERROR] Arquivo " + listOfFiles[i] + " selecionado não possuí criptografia compatível.");
                       totalNoDecriptedFile ++;
                    }
                // incrementa progressBar
                MainFrame.progressBarStart.setMaximum(listOfFiles.length);    
                MainFrame.progressBarStart.setValue(MainFrame.progressBarStart.getValue() + 1);
                
                if(MainFrame.threadSleep == true){
                     System.out.println(Thread.currentThread().getName());
                    return totalDecriptedFile;
                }
            }else{
                    System.out.println("nao e Arquivo " + listOfFiles[i].getName());
            }
        } 
        return listOfFiles.length - totalNoDecriptedFile;//listOfFiles.length - totalNoDecriptedFile;
    }   
}
