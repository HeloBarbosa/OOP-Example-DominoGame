package trabalho2;

public class Peca
{
	private int parteA;
	private int parteB;

	public Peca(int parteA, int parteB)
	{
		this.parteA = parteA;
		this.parteB = parteB;

	}

	public int getParteA()
	{
		return parteA;
	}

	public void setParteA(int parteA)
	{
		this.parteA = parteA;
	}

	public int getParteB()
	{
		return parteB;
	}

	public void setParteB(int parteB)
	{
		this.parteB = parteB;
	}

	public boolean carroca()
	{
		if (this.parteA == this.parteB)
		{
			return true;
		}
		return false;
	}

	public int valorPeca()
	{
		return parteA + parteB;
	}
	
	public void virarPeca() 
	{
		int newParteA = this.parteB;
		int newParteB = this.parteA;
		this.parteA = newParteA;
		this.parteB = newParteB;
	}
	
	public void printPeca()
	{
		if (this.carroca() == false)
		{
			System.out.println("|" + this.parteA + "|");
			System.out.println("---");
			System.out.println("|" + this.parteB + "|");
			System.out.println();
		}
		else
		{
			System.out.println("|" + this.parteA + "|" + this.parteB + "|");
			System.out.println();

		}
	}
	
	public String miniPrint()
	{
		return "|"+this.parteA+"|" + this.parteB + "|";
	}

}
