package trabalho2;

import java.util.*;

public class Main
{
	public static void main(String args[])
	{
		boolean endGame = false;

		System.out.println("// ------------------------ Dominó ------------------------ \\\\");
		System.out.println();


		// Inicializar partida e jogo
		Partida partida = new Partida();
		while (endGame == false)
		{
			partida.novaPartida();
			if (partida.fimJogo() == true)
			{
				endGame = true;
			}
		}

	}
}
