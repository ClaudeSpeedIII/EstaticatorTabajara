import java.util.*;

public class EstaticatorTabajara {
		
	public static void main (String[] args) {
		//REFERENTE A INICIALIZACAO E APRESENTACAO
		Scanner entrada = new Scanner(System.in); //Inicializa o objeto scanner, que será utilizado para inserir textos

		System.out.println("Bem vindo ao Estaticator Tabajara, utilitário de pequenos e simples calculos estatisticos\npor Pedro Lucas Nascimento Christensen\n\nDigite um valor seguido de Enter para adicionar a lista,\nou deixe o campo em branco e pressione enter para terminar\n");
		
		List<Float> amostra = new ArrayList<Float>(); //Inicializa a lista com os números com que trabalharemos
		
		String texto = null; //String de entrada
		
		//REFERENTE A ENTRADA DE DADOS
		while(!(texto = entrada.nextLine()).equals("")) { //Loop de entrada de texto que será executado até o usuario apertar enter sem digitar nada, o que caracteriza uma string vazia
			amostra.add(Float.valueOf(texto)); //Converte o texto que foi entrado para Float e insere na lista
		}
		
		System.out.println("\nEntrada de dados finalizada\n\n"); //Avisa o término do loop e consequentemente da entrada de dados
		
		//Inicializa algumas variaveis
		float media = 0; //Para armazenar a média
		float somaTudo = 0; //Soma de toda a lista, para calculo da média
		float maior = Float.MIN_VALUE * -1; //Maior item da lista. Atribuido o menor valor possivel para variavel do tipo float, para permitir achar o maior valor entre valores muito negativos

	
		//REFERENTE AO CALCULO DE MEDIA
		for(Float numero : amostra) { //Primeiro loop da lista
			somaTudo += numero; //Vai somando, para permitir o calculo da média
			if(numero > maior) { maior = numero; } //Vai substituindo o valor da variavel maior, pelo item atual, caso for maior que o ultimo valor
		}
		
		media = somaTudo / amostra.size(); //Calcula a média de todos os itens da lista
		
		//REFERENTE AO CALCULO DE VARIANCIA
		somaTudo = 0; //Zera a variavel para uso posterior no calculo da variancia
		float variancia = 0; //Declara a variavel pra armazenar a Variancia
		float menor = maior; //Menor item da lista, com o maior associado para funcionar em todos os tipos de entradas
		double desvioPadrao = 0; //Declara a variavel para armazenar o Desvio Padrao
		
		System.out.println("Média: " + media); //Exibe o resultado da média
		
		for(Float numero : amostra) { //Segundo loop pela lista
			somaTudo += (numero - media) * (numero - media); //Reusa o somaTudo (que fora utilizado pra calcular a média) para somar o quadrado de cada numero da lista menos a media
			if(numero < menor) { menor = numero; } //Aproveita e já tira o menor número da lista
		}
		
		Collections.sort(amostra); //Organiza a lista em ordem crescente para calculo da mediana e para facilitar a compreensão do calculo da moda, ao fazer o teste de mesa
		
		//REFERENTE A MODA
		List<Float[]> numerosUnicos = new ArrayList<Float[]>(); //Cria uma lista de vetores que vai conter os numeros da lista de trabalho, sem repetição. Os vetores dentro da lista terão duas casas. Uma para o número em sí, e outro pra pontuação (quantidade de vezes em que já apareceu)
		Float[] score = null; //Esse é o vetor que vai ser armazenado dentro de cada entrada da lista de números unicos.
		
		for(Float numero : amostra) { //Terceiro loop pela lista, agora em ordem crescente
			boolean jaApareceu = false; //Variavel para poder verificar se o atual número já apareceu na lista de números unicos
			for(Float[] numeroUnico : numerosUnicos) { //Primeiro loop na lista de numeros unicos
				if((float) numeroUnico[0] == (float) numero) { jaApareceu = true; break; } //Efetivamente verifica se já apareceu ou não na lista, se sim, marca a flag jaApareceu como positivo e termina o loop
			}
			if(!jaApareceu) { //Se nunca apareceu
				score = new Float[2]; //Instancia um novo vetor
				score[0] = numero; //Armazena qual o número em questão
				score[1] = 1f; //Na posição de quantidade de aparições, armazena 1, por ser a primeira aparição
				numerosUnicos.add(score); //Adiciona na lista de numeros unicos
			} else { //Se já apareceu
				for(Float[] numeroUnico : numerosUnicos) { //Segundo loop na lista de numeros unicos
					if((float) numeroUnico[0] == (float) numero) { //Serve para caçar o numero com qual estamos trabalhando. Caso for positivo..
						numeroUnico[1]++; //Soma mais um na posição de quantidade de aparições
						break; //E sai do loop
					}
				}
			}
		}
		
		float maiorContagem = 1; //Se tiver uma entrada na lista, a maior contagem vai ser ao menos 1
		float moda = menor; //Isso aqui é para assegurar que se tiver duas modas quanto a pontuação, a de maior número será a exibida
		
		for(Float[] numeroUnico : numerosUnicos) { //Loop final pela lista de números unicos
			if(numeroUnico[1] >= maiorContagem && numeroUnico[0] >= moda) { maiorContagem = numeroUnico[1]; moda = numeroUnico[0]; } //Caso o numero de aparicoes seja o maior até então, E, o numero seja o maior também até então, atribuir os valores correspondentes
		}
		
		//REFERENTE A MEDIANA
		float mediana = 0; //Declara a variavel para armazenar a mediana
		
		if(amostra.size() % 2 == 0) { //Se o número for par, executa o seguinte
			mediana = (amostra.get((int) ((amostra.size() / 2) - 0.5)) + amostra.get((int) ((amostra.size() / 2) + 0.5))) / 2;	//Pega o valor do meio, calculando a média no processo
		} else { //Caso contrário, o número é impar e se deve executar o seguinte
			mediana = amostra.get((int) ((amostra.size() / 2) + 0.5));	//Pega o valor do meio, absoluto
		}
		
		//REFERENTE A VARIANCIA E DESVIO PADRAO
		variancia = somaTudo / (amostra.size() - 1); //Efetivamente calcula a variancia
		desvioPadrao = Math.sqrt(variancia); //Faz a raiz quadrada da variancia e atribui a variavel desvioPadrao
		
		//REFERENTE A EXIBICAO DOS RESULTADOS
		System.out.println("Variancia: " + variancia + "\nDesvio Padrao: " + desvioPadrao); //Exibe a variancia e o desvio padrão
		System.out.print("Maior: " + maior + "\nMenor: " + menor + "\nMediana: " + mediana + "\nModa: "); //Exibe o Maior, o menor e a Mediana
		if(maiorContagem > 1) { System.out.println(moda); } else { System.out.println("Sem repetição de números"); } //Exibe a moda ou avisa se não houver
		
	}
}