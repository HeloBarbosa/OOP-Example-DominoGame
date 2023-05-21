package trabalho2;

import java.util.*;

public class Tabuleiro
{
	private ArrayList<Peca> pecasTabuleiro = new ArrayList<Peca>();
	private int parteATabuleiro;
	private int parteBTabuleiro;

	public Tabuleiro()
	{
	
	}

	public ArrayList<Peca> getPecasTabuleiro()
	{
		return pecasTabuleiro;
	}

	public void setPecasTabuleiro(ArrayList<Peca> pecasTabuleiro)
	{
		this.pecasTabuleiro = pecasTabuleiro;
	}

	public int getParteATabuleiro()
	{
		this.pegarPartes();
		return parteATabuleiro;
	}

	public void setParteATabuleiro(int parteATabuleiro)
	{
		this.parteATabuleiro = parteATabuleiro;
	}

	public int getParteBTabuleiro()
	{
		this.pegarPartes();
		return parteBTabuleiro;
	}

	public void setParteBTabuleiro(int parteBTabuleiro)
	{
		this.parteBTabuleiro = parteBTabuleiro;
	}

	public void adicionarPecas(Peca peca)
	{
		if (this.tabuleiroVazio()==true) 
		{
			this.pecasTabuleiro.add(peca);
		}
		else if (this.getParteBTabuleiro()==peca.getParteA()) 
		{
			this.pecasTabuleiro.add(peca);
		}
		else if (this.getParteBTabuleiro()==peca.getParteB()) 
		{
			peca.virarPeca();
			this.pecasTabuleiro.add(peca);
		}
		else if (this.getParteATabuleiro()==peca.getParteA()) 
		{
			peca.virarPeca();
			// adicionar ao inicio do tabuleiro
			this.pecasTabuleiro.add(0,peca);		
		}
		else if(this.getParteATabuleiro()==peca.getParteB()) 
		{
			this.pecasTabuleiro.add(0,peca);
		}
	}

	public void printTabuleiro()
	{
		System.out.println("-----------Tabuleiro----------");
		System.out.println();
		if(this.tabuleiroVazio()==true) {
			System.out.println("O tabuleiro ainda não tem peças");
			System.out.println();
		}
		for (int i = 0; i < this.pecasTabuleiro.size(); i++)
		{
			Peca peca = this.pecasTabuleiro.get(i);
			peca.printPeca();
		}
	}

	public void pegarPartes() // Atualizar as partes
	{
		// Pegar a primeira parte da primeira peça do tabuleiro
		this.parteATabuleiro = this.pecasTabuleiro.get(0).getParteA(); 
		// Pegar a última parte da última peça do tabuleiro
		this.parteBTabuleiro = this.pecasTabuleiro.get(this.pecasTabuleiro.size() - 1).getParteB();
	}

	public boolean tabuleiroVazio()
	{
		if (this.pecasTabuleiro.size() == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
