/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ApresentacaoInterface.java
Data: 23.06.2024
--------------------------------------*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ApresentacaoInterface extends  JFrame {
	
	public ApresentacaoInterface(){
		setTitle("Tela de boas vindas")
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public static void main(String args[]){
		Vector_Tabelas.inic();
	}
	
}