package trabalho2;

import java.util.*;

public class Caixa
{
	private ArrayList<Peca> caixaPecas = new ArrayList<Peca>();
	private int numeroPecas;

	public Caixa()
	{
		this.numeroPecas = 28;

		for (int parteB = 0; parteB < 7; parteB++)
		{
			for (int parteA = 0; parteA < parteB + 1; parteA++)
			{
				Peca peca = new Peca(parteA, parteB);
				caixaPecas.add(peca);
			}
		}
	}

	public ArrayList<Peca> getCaixaPecas()
	{
		return caixaPecas;
	}

	public void setCaixaPecas(ArrayList<Peca> caixaPecas)
	{
		this.caixaPecas = caixaPecas;
	}

	public int getNumeroPecas()
	{
		return numeroPecas;
	}

	public void setNumeroPecas(int numeroPecas)
	{
		this.numeroPecas = numeroPecas;
	}

	public ArrayList<Peca> darPecaCaixa()
	{
		return this.caixaPecas;
	}

}
