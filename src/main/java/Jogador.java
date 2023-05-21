package trabalho2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Jogador
{
	private String Nome;
	private boolean Robo;
	private ArrayList<Peca> pecasMao = new ArrayList<Peca>();

	public Jogador(String nome, boolean robo)
	{
		this.Nome = nome;
		this.Robo = robo;
	}

	public String getNome()
	{
		return Nome;
	}

	public void setNome(String nome)
	{
		Nome = nome;
	}

	public boolean isRobo()
	{
		return Robo;
	}

	public void setRobo(boolean robo)
	{
		Robo = robo;
	}

	public void embaralharDistribuir(Caixa caixa, Jogador jogador1, Jogador jogador2, Jogador jogador3, Jogador jogador4)
	{
		// Pegar caixa
		ArrayList<Peca> caixaPecas = caixa.darPecaCaixa();
		// Embaralhar peças da caixa
		Collections.shuffle(caixaPecas);
		// Distribuir peças para os jogadores
		for (int i = 0; i < 7; i++) 
		{
			jogador1.receberPecas(caixaPecas.get(0));
			caixaPecas.remove(0);
		}
		System.out.println(jogador1.getNome()+" recebeu peças.");
		
		for (int i = 0; i < 7; i++)
		{
			jogador2.receberPecas(caixaPecas.get(0));
			caixaPecas.remove(0);
		}
		System.out.println(jogador2.getNome()+" recebeu peças.");
		
		for (int i = 0; i < 7; i++)
		{
			jogador3.receberPecas(caixaPecas.get(0));
			caixaPecas.remove(0);
		}
		System.out.println(jogador3.getNome()+" recebeu peças.");
		
		for (int i = 0; i < 7; i++)
		{
			jogador4.receberPecas(caixaPecas.get(0)); // este é o que embaralha e distribui, posso trocar por this.pecasMado.add
			caixaPecas.remove(0);
		}
		System.out.println(jogador4.getNome()+" deu peças a sí mesmo.");
		System.out.println();
	}

	public void receberPecas(Peca peca)
	{
		this.pecasMao.add(peca);
	}


	public boolean temPeca()
	{
		if (this.pecasMao.size() > 0)
		{
			return true;
		}
		return false;
	}

	public int valorTotalPecas()
	{
		// Ver o numero de pontos na mao do jogador
		int soma = 0;
		for (int j = 0; j < this.pecasMao.size(); j++)
		{
			soma = soma + this.pecasMao.get(j).valorPeca();
		}
		
		//Se a peça 0-0 for a única peça na mão do jogador, ela vale 10 e não zero!			
		if(this.pecasMao.size()==1 && this.pecasMao.get(0).valorPeca()==0)
		{	
			soma = 10;	
		}		
		return soma;
	}

	public boolean jogar(Tabuleiro tabuleiro) 
	{
		boolean jogada;
		if(this.Robo==true) 
		{
			//Jogar como robô
			jogada = this.jogarRobo(tabuleiro);	
		}
		else 
		{
			//Jogador humano
			jogada = this.jogarHumano(tabuleiro);
		}
		return jogada;
	}
	
	public boolean jogarRobo(Tabuleiro tabuleiro)
	{
		//Ir na lista de peças da mão do robô, fazer a soma de cada peça, se a anterior for maior do que a posterior
		//a peça que deve ser escolhida é essa
		Peca peca = new Peca(0,0);
		int sum = 0;
		for(int i=0;i<this.pecasMao.size(); i++)
		{
			// Se a jogada for permitida, se o valor da peça é maior que das outras e se o tabuleiro nao estiver vazio:
			if (this.jogadaPermitida(tabuleiro, this.pecasMao.get(i))==true && this.pecasMao.get(i).valorPeca()>sum) 
			{
				peca = this.pecasMao.get(i);
				sum = peca.valorPeca();
			}
		}
		// Caso a unica peca seja |0|0|
		if (sum==0 && this.pecasMao.size()==1)
		{
			peca = this.pecasMao.get(0);
			this.jogarPeca(tabuleiro, peca);
			System.out.println(this.Nome +" jogou peça "+peca.miniPrint());
			return true;
		}
		// Caso nao de pra jogar nenhuma peca
		else if (sum==0)
		{
			//Pula a vez
			System.out.println(this.Nome+" passou a vez.");	
			return false;
		}
		else 
		{
			this.jogarPeca(tabuleiro, peca);
			System.out.println(this.Nome+" jogou peça "+peca.miniPrint());			
			return true;
		}
	}
	
	public boolean jogarHumano(Tabuleiro tabuleiro) 
	{	
		// Fazer print do tabuleiro e da mao
		System.out.println("-> É a sua vez de jogar:");
		System.out.println();
		tabuleiro.printTabuleiro();
		this.printMao();
		// Pedir input do jogador humano
		int jogada = this.pedirJogada();
		// Para o caso que o jogador queira passar a jogada
		if (jogada == this.pecasMao.size()) 
		{
			System.out.println("Voçê passou a jogada! Que pena!");
			return false;
		}
		else
		{
			// Verificar Jogada
			while (this.jogadaPermitida(tabuleiro, this.pecasMao.get(jogada)) == false)
			{		
				System.out.println("Opção inválida, jogue novamente.");
				System.out.println();
				jogada = this.pedirJogada();
				if (jogada == this.pecasMao.size()) 
				{
					System.out.println("Voçê passou a jogada! Que pena!");
					return false;
				}
			}
			// Jogar
			this.jogarPeca(tabuleiro, this.pecasMao.get(jogada));
			return true;
		}
	}

	public void jogarPeca(Tabuleiro tabuleiro, Peca peca)
	{
		tabuleiro.adicionarPecas(peca);
		this.pecasMao.remove(peca);
	}

	public boolean jogadaPermitida(Tabuleiro tabuleiro, Peca peca)
	{
		if (this.temPeca() == true) // ver se ganhou
		{
			if (tabuleiro.tabuleiroVazio()==true)
			{
				return true; 
			}
			else if (tabuleiro.getParteATabuleiro()==peca.getParteA() || tabuleiro.getParteBTabuleiro()==peca.getParteB() || tabuleiro.getParteATabuleiro()==peca.getParteB() || tabuleiro.getParteBTabuleiro()==peca.getParteA()) {
				return true;
			}
		}
		else
		{
			return false;
		}
		return false;
	}

	public void printMao()
	{
		System.out.println("------------Sua mão-----------");
		System.out.println();
		for (int i = 0; i < this.pecasMao.size(); i++)
		{
			System.out.println(i + "):");
			Peca peca = this.pecasMao.get(i);
			peca.printPeca();
		}
		System.out.println(this.pecasMao.size() + "):");
		System.out.println("Pass"); // Temos que pensar em como selecionar a última opção
		System.out.println();
	}
	
	public int pedirJogada()
	{
		// Pedir jogada do jogador humano
		boolean menu = true;
		int jogada = 10;
		Scanner scan = new Scanner(System.in);
		while (menu == true)
		{
			System.out.println("Selecione o seu movimento:");
			if (scan.hasNextInt())
			{
				jogada=scan.nextInt();
				scan.nextLine();
				if(jogada<0 || jogada>this.pecasMao.size()) 
				{
					System.out.println("Opção inválida! Tente de novo!");
				}
				else 
				{
				menu =false;
				}
			}
			else
			{ 
				scan.nextLine();
				System.out.println("Inuput incorreto.Tente novamente!");
				menu = true;
			}
		}
		return jogada;
	}
}
