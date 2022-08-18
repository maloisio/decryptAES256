/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.velsis.velsisdecrypt.cryptography;

import static com.velsis.velsisdecrypt.cryptography.Aes256.decodeFileToBase64Binary;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author msmaniotto
 */
public class Encrypt {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        File dir = new File("/home/msmaniotto/velsisdec/");
        //String[] paths = dir.list();
        File[] listOfFiles = dir.listFiles();
        //boolean files = dir.isFile();
        
        //listando somente os arquivos dentro da pasta teste123
        for (int i = 0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isFile()){
                System.out.println("File " + listOfFiles[i].getName());
            }
        }
        
        //cria diretorio
        String dirname = "/home/msmaniotto/velsisdec/encript";
        File d = new File(dirname);
        d.mkdirs();
        
        //encriptando todos os arquivos dentro da pasta teste123
        String[] originalString = new String[1000];
        String[] encryptedString = new String[1000];

        
        for(int i = 0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isFile()){
                File f = new File("/home/msmaniotto/velsisdec/" + listOfFiles[i].getName()); //("/home/msmaniotto/teste123/" + listOfFiles[i].getName());
            
                originalString[i] = Aes256.encodeFileToBase64Binary(f); //converte arquivo (bytes) em string

                encryptedString[i] = Aes256.encrypt(originalString[i]); //encripta string
        
                System.out.println(originalString[i]); //string arquivo
                System.out.println(encryptedString[i]); //string encriptada por AES do arquivo
                System.out.println("\n"); //string encriptada por AES do arquivo

                //cria arquivo (bytes) a partir da string encriptada
                byte[] decode = decodeFileToBase64Binary(encryptedString[i]); 
                FileOutputStream outFile = new FileOutputStream( //cria arquivo
                    "/home/msmaniotto/velsisdec/encript/"+listOfFiles[i].getName());
        
                outFile.write(decode);//escreve no arquivo
            }else{
                System.out.println("nao e File " + listOfFiles[i].getName());
            }
        }
    }
}
//        File f =  new File("/home/msmaniotto/teste123/arquivoteste");
//        String originalString = AES256.encodeFileToBase64Binary(f); //converte arquivo (bytes) em string
//
//        String encryptedString = AES256.encrypt(originalString); //encripta string
//        
//        System.out.println(originalString); //string arquivo
//        System.out.println(encryptedString); //string encriptada por AES do arquivo
   
        //cria diretorio
//        String dirname = "/home/msmaniotto/teste123/encript";
//        File d = new File(dirname);
//        d.mkdirs();
//        
//        //cria arquivo (bytes) a partir da string encriptada
//         byte[] decode = decodeFileToBase64Binary(encryptedString); 
//                FileOutputStream outFile = new FileOutputStream( //cria arquivo
//                    "/home/msmaniotto/teste123/encript/encript_file");
//        
//        outFile.write(decode);//escreve no arquivo