package PokerFuncs;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Esta classe foi desenhada para facilitar a geração de números inteiros.
 *
 * @author João Pedro Almeida Santos Secundino e delamaro
 *
 */
public class Random {
	
	private long p = 2147483648L;
	private long m = 843314861;
	private long a = 453816693;
	
	private long xi = 1023; //semente

	/**
	 * @return numero aleatorio [0, 1)
	 * */
	public double getRand(){
		xi = (a + m*xi)%p;
		return xi/(double)p;
	}

	/**
	* @return numero aleatorio [0, max)
	* */
	public int getIntRand(int max){
		double  x = getRand();
		return (int)(x*max);
	}
	/**
	 * Muda a semente do gerador para k
	 * */
	public void setSeed(int k){
		xi = k;
	}

	/**
	 * Constroi um gerador com a semente k
	 * */
	public Random(int k){
		xi = k;
	}

//	/**
//	 * Constroi um gerador com uma semente aleatoria
//	 * @throws InterruptedException
//	 * */
	public Random() throws InterruptedException{
		xi =  Calendar.getInstance().getTimeInMillis();
		try {
			TimeUnit.MICROSECONDS.sleep(1300);
		}catch (InterruptedException x) {};
	}
}
