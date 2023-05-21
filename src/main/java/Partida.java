package trabalho2;

import java.util.*;

public class Partida
{
	private String nomeJogador;
	private String nomeRobo1;
	private String nomeRobo2;
	private String nomeRobo3;
	private ArrayList<Jogador> listaJogadores = new ArrayList<Jogador>();
	private ArrayList<Integer> listaOrdemJogador = new ArrayList<Integer>(Arrays.asList(0,0,0,0));
	private ArrayList<Integer> listaOrdemDistribuidor = new ArrayList<Integer>(Arrays.asList(0,0,0,0));
	private ArrayList<Integer> listaPontosJogadores = new ArrayList<Integer>(Arrays.asList(0,0,0,0));
	private ArrayList<Boolean> listaParidaEncalhada = new ArrayList<Boolean>(Arrays.asList(true,true,true,true));
	private boolean primeiraPartida;
	private boolean jogada;
	private int primeiroJogador;
	private int distribuidor;
	private int vencedorRodada;
	
	private Caixa caixa;
	private Tabuleiro tabuleiro;
	private Jogador jogador;
	private Jogador robo1;
	private Jogador robo2;
	private Jogador robo3;
	
	public Partida()
	{
		this.nomeJogador = this.pedirNome();
		this.nomeRobo1 = this.nomeAleatorio();
		this.nomeRobo2 = this.nomeAleatorio();
		this.nomeRobo3 = this.nomeAleatorio();
		this.primeiraPartida = true;
		this.jogada = true;
	}
	
	public void novaPartida()
	{	
		System.out.println();
		System.out.println("======= Nova Partida =========");

		// Esta função vai criar um jogo novo, um tabuleiro novo e uma caixa nova para cada partida
		caixa = new Caixa();
		tabuleiro = new Tabuleiro();

		// Criar jogadores
		jogador = new Jogador("Jogador ("+this.nomeJogador+")", false);
		robo1 = new Jogador("Robo 1 ("+this.nomeRobo1+")", true);
		robo2 = new Jogador("Robo 2 ("+this.nomeRobo2+")", true);
		robo3 = new Jogador("Robo 3 ("+this.nomeRobo3+")", true);
		
		// Se for primeira jogada escolher o primeiro jogador e o distribuidor aleatoriamente
		if (this.primeiraPartida==true)
		{		
			this.primeiroJogador = escolherJogadorAleatorio(4);
			this.distribuidor = escolherJogadorAleatorio(4);
		
			// Preencher listas
			this.listaJogadores.add(jogador);
			this.listaJogadores.add(robo1);
			this.listaJogadores.add(robo2);
			this.listaJogadores.add(robo3);
		}
		else
		{
			// Nas seguintes partidas o distribuidor é escolhido ciclicamente
			if (this.distribuidor==3) {this.distribuidor = 0;}
			else {this.distribuidor += 1;}

			// Preencher listas
			this.listaJogadores.set(0, jogador);
			this.listaJogadores.set(1, robo1);
			this.listaJogadores.set(2, robo2);
			this.listaJogadores.set(3, robo3);
		}
		
		// Cria lista usada para ordem de jogadores e distribuição
		this.criarListaOrdemJogador();
		this.criarListaOrdemDistribuidor();
		
		// Fazer print da ordem dos jogadores e distribuição
		this.printOrdemJogador();
		this.printOrdemDistribuidor();
			
		// Distribuidor vai distribuir peças
		int d0 = this.listaOrdemDistribuidor.get(0);
		int d1 = this.listaOrdemDistribuidor.get(1);
		int d2 = this.listaOrdemDistribuidor.get(2);
		int d3 = this.listaOrdemDistribuidor.get(3);
		listaJogadores.get(d0).embaralharDistribuir(caixa, listaJogadores.get(d1), listaJogadores.get(d2), listaJogadores.get(d3), listaJogadores.get(d0));
		
		// Fazer uma nova jogada
		boolean partidaOn = true;
		boolean encalhada = false;
		int rodada = 1;
		while(partidaOn==true) 
		{	
			System.out.println();
			System.out.println("-----------Rodada "+ rodada +"-----------");
			System.out.println();
			// Jogadores vão jogar na ordem estabelecida
			for (int i=0; i<4; i++)
			{
				if (partidaOn == true)
				{
					// Jogar e Ver se o jogador está encalhado
					jogada = this.listaJogadores.get(this.listaOrdemJogador.get(i)).jogar(tabuleiro);
					// Ver se é vencedor
					if (this.listaJogadores.get(this.listaOrdemJogador.get(i)).temPeca()==false) 
					{
						// Vencedor da partida!
						System.out.println();
						System.out.println("-------Fim da partida!--------");
						System.out.println();
						System.out.println(this.listaJogadores.get(this.listaOrdemJogador.get(i)).getNome()+" venceu a partida com "+this.listaJogadores.get(this.listaOrdemJogador.get(i)).valorTotalPecas()+" pontos!");
						System.out.println();
						vencedorRodada = this.listaOrdemJogador.get(i);
						partidaOn = false;
					}
					// Se o jogador pular a vez ou nao, adiciona true ou false a lista
					this.listaParidaEncalhada.set(this.listaOrdemJogador.get(i), this.jogada);
				}
			}
			// Ver se a partida está encalhada (listaParidaEncalhada = [false,false,false,false])
			if(this.listaParidaEncalhada.get(0)==false && this.listaParidaEncalhada.get(1)==false && this.listaParidaEncalhada.get(2)==false && this.listaParidaEncalhada.get(3)==false) {
				System.out.println();
				System.out.println("*----------------------------*");
				System.out.println("......Partida encalhada!......");
				System.out.println("*----------------------------*");
				System.out.println();
				System.out.println("-------Fim da partida!--------");
				System.out.println();
				partidaOn = false;
				encalhada = true;
			}
			rodada += 1;
		}
		
		// Para o caso em que a partida tenha encalhado
		if (encalhada == true)
		{
			// Ver se a partida encalhou com empate
			ArrayList<Integer> listaEncalhados = new ArrayList<Integer>();
			for (int i=0; i<this.listaJogadores.size(); i++)
			{
				listaEncalhados.add(this.listaJogadores.get(i).valorTotalPecas());
			}
			listaEncalhados = this.acharMinimos(listaEncalhados);
			// Escolher o primeiro jogador da nova partida aleatorio entre os encalhados empatados ou o unico vencedor encalhado
			Random ran1 = new Random();
			this.primeiroJogador = listaEncalhados.get(ran1.nextInt(listaEncalhados.size()));
			// Fazer print dos encalhados empatados
			if (listaEncalhados.size()>1)
			{
				String fraseEncalhados = "Jogadores";
				for (int i=0; i<listaEncalhados.size(); i++)
				{
					fraseEncalhados += " "+this.listaJogadores.get(listaEncalhados.get(i)).getNome()+ " e";
				}
				fraseEncalhados = fraseEncalhados.substring(0, fraseEncalhados.length()-1) + "empataram encalhados com "+this.listaJogadores.get(listaEncalhados.get(0)).valorTotalPecas()+" pontos";
				System.out.println(fraseEncalhados);
				System.out.println();
			}
			else
			{
				System.out.println(this.listaJogadores.get(listaEncalhados.get(0)).getNome()+" venceu a partida encalhada com "+this.listaJogadores.get(listaEncalhados.get(0)).valorTotalPecas()+" pontos!");
			}
		}
		
		// Adicionar pontuacao de cada jogador e fazer print da pontuacao de cada partida
		System.out.println("------Pontuacao Partida-------");
		for (int i=0; i<this.listaJogadores.size(); i++)
		{
			System.out.println(this.listaJogadores.get(i).getNome()+": "+this.listaJogadores.get(i).valorTotalPecas()+" pontos");
			this.listaPontosJogadores.set(i, this.listaJogadores.get(i).valorTotalPecas() + this.listaPontosJogadores.get(i));
		}
		System.out.println("------------------------------");
		
		// Fazer print dos pontos acumulados
		System.out.println("-----Pontuacao acumulada------");
		for (int i=0; i<this.listaJogadores.size(); i++)
		{
			System.out.println(this.listaJogadores.get(i).getNome()+": "+this.listaPontosJogadores.get(i)+" pontos");
		}
		System.out.println();
		System.out.println("==============================");		
		
		// Escolher o primeiro jogador da nova partida
		if (encalhada == false) {this.primeiroJogador = this.vencedorRodada;}
		// Fim da primeira partida
		this.primeiraPartida = false;
	}

	public String pedirNome()
	{
		// Pedir nome do jogador humano
		boolean menu = true;
		String nome = " ";
		Scanner scan = new Scanner(System.in);
		while (menu == true)
		{
			System.out.println("Por favor, insira o seu nome: ");
			if (scan.hasNextInt())
			{
				scan.nextLine();
				System.out.println("Inuput incorreto.Tente novamente!");
				menu = true;
			}
			else
			{
				nome = scan.nextLine();
				menu = false;
			}
		}
		return nome;
	}
	
	public int escolherJogadorAleatorio(int num)
	{
		// Escolher um jogador aleatoriamente
		Random ran = new Random();
		return ran.nextInt(num);
	}
	
	public void criarListaOrdemJogador() 
	{
		// Cria lista usada para ordem de jogadores
		int i = this.primeiroJogador;
		for (int j=0; j<4; j++)
		{
			this.listaOrdemJogador.set(j, i);
			if (i==3) {i = 0;}
			else {i += 1;}
		}
	}
	
	public void criarListaOrdemDistribuidor() 
	{
		// Cria lista usada para ordem de distribuição
		int i = this.distribuidor;
		for (int j=0; j<4; j++)
		{
			this.listaOrdemDistribuidor.set(j, i);
			if (i==3) {i = 0;}
			else {i += 1;}
		}
	}
	
	public void printOrdemJogador()
	{	
		System.out.println();
		System.out.println("-------Ordem de Jogada--------");
		for(int i=0; i<this.listaOrdemJogador.size(); i++) 
		{
			System.out.println(this.listaJogadores.get(this.listaOrdemJogador.get(i)).getNome());
		}
	}
	
	public void printOrdemDistribuidor()
	{
		System.out.println();
		System.out.println("----Ordem de Distribuição-----");
		for(int i=1; i<this.listaOrdemDistribuidor.size(); i++) 
		{
			System.out.println(this.listaJogadores.get(this.listaOrdemDistribuidor.get(i)).getNome());
		}
		System.out.println("Distribuidor: "+this.listaJogadores.get(this.listaOrdemDistribuidor.get(0)).getNome());
		System.out.println("==============================");
		System.out.println();
	}
	
	public boolean fimJogo() {
		boolean fim = false;
		boolean empate = false;
		int jogadorVencedor=0;
		int sum = 50;
		// Ver se algum jogador tem mais que 50 pontos
		for (int i=0; i<this.listaPontosJogadores.size(); i++) 
		{
			if(this.listaPontosJogadores.get(i)>=50)
			{
				fim = true;
			}
			// Ver qual é o jogador de menor pontuaçao
			if (sum > this.listaPontosJogadores.get(i))
			{
				sum = this.listaPontosJogadores.get(i);
				jogadorVencedor = i;
			}
		}
		// Verificar se empatou
		ArrayList<Integer> empatantes = this.acharMinimos(this.listaPontosJogadores);
		if (empatantes.size() > 1) 
		{
			empate=true;
		}
		// Quando o jogo acabar
		if(fim==true) 
		{	
			System.out.println("******************************");
			System.out.println("---------Fim de jogo!---------");
			System.out.println("******************************");
			
			if(empate==false)
			{
				System.out.println();
				System.out.println("*------------------------------*");
				System.out.println("O vencedor do jogo é "+this.listaJogadores.get(jogadorVencedor).getNome()+"!!!");
				System.out.println("*------------------------------*");
				System.out.println();
			}
			else
			{	System.out.println();
				System.out.println("*----------------------------*");
				System.out.println("           Empate!			  ");
				System.out.println("*----------------------------*");
				System.out.println();
				String fraseEmpate = "Jogadores";
				for (int i=0; i<empatantes.size(); i++)
				{
					fraseEmpate += " "+this.listaJogadores.get(empatantes.get(i)).getNome()+ " e";
				}
				fraseEmpate = fraseEmpate.substring(0, fraseEmpate.length()-1) + "empataram!";
				System.out.println(fraseEmpate);
				System.out.println();
			}
			// Print da pontuaçao final
			System.out.println("-------Pontuacao Final--------");
			for (int i=0; i<this.listaPontosJogadores.size(); i++) 
			{
				System.out.println(this.listaJogadores.get(i).getNome()+": "+this.listaPontosJogadores.get(i)+" pontos");
			}
		}
		return fim;
	}
	
	public ArrayList<Integer> acharMinimos(ArrayList<Integer> lista)
	{
		// Recebe uma lista e acha os indices dos minimos
		int indiceMinimo = 0;
		ArrayList<Integer> minimos = new ArrayList<Integer>();
		for (int i = 0; i < lista.size(); i++)
		{
		    if (lista.get(i) == lista.get(indiceMinimo))
		    {
		        minimos.add(i);
		    } else if (lista.get(indiceMinimo) > lista.get(i))
		    {
		    	minimos.clear();
		        minimos.add(i);

		        indiceMinimo = i;
		    }
		}
		return minimos;
	}
	
	//Dar nomes aleatórios aos Robôs
	public String nomeAleatorio()
	{
		String[] nomes = {"Maria", "José", "Luís", "Paulo", "Giorgio", "Julia", "Ana", "Julio", "João", "Emanuel", "Elisa", "Miguel", "Guilherme", "Manuel", "Telma", "Tiago", "Marcela", "Inês", "Ulson"};
		Random ran = new Random();
		int indice = ran.nextInt(nomes.length);
		return nomes[indice];
	}

}
