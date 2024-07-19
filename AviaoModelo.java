/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: AviaoModelo.java
Data: 09.06.2024
--------------------------------------*/

import java.io.Serializable;

public class AviaoModelo implements Serializable
{
	private int id, numDeAssentos;
	private StringBufferModelo modelo, companhiaAerea;
	
	public AviaoModelo(int id, int numDeAssentos, String modelo, String companhiaAerea)
	{
		this.id = id;
		this.numDeAssentos = numDeAssentos;
		this.modelo = new StringBufferModelo(modelo);
		this.companhiaAerea = new StringBufferModelo(companhiaAerea);
	}
	
	//metodos get
	public int getId()
	{
		return id;
	}
	
	public int getNumDeAssentos()
	{
		return numDeAssentos;
	}
	
	public String getModelo()
	{
		return modelo.get();
	}
	
	public String getCompanhiaAerea()
	{
		return companhiaAerea.get();
	}
	
	//metodos set
	public void setNumDeAssentos(int numDeAssentos)
	{
		this.numDeAssentos = numDeAssentos;
	}
	
	public void setModelo(String modelo)
	{
		this.modelo.set(modelo);
	}
	
	public void setCompanhiaAerea(String companhiaAerea)
	{
		this.companhiaAerea.set(companhiaAerea);
	}
	
	  public String toString() {
        return id + " - " + modelo;
    }
}
