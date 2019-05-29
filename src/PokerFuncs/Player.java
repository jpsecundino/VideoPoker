package PokerFuncs;

/**
 * Esta classe foi desenhada para tornar concreta a interface de um jogador.
 *
 * @author João Pedro Almeida Santos Secundino
 *
 */
public class Player {

    private int coins;
    private Card[] hand;

    /**
     * Inicializa um jogador com 200 moedas
    * */
    public Player(){
        this.coins = 200;
    }

    /**
     * Inicializa um jogador com "coins" moedas
    * */
    public Player(int coins){
        this.coins = coins;
    }

    /**
     * @return quantidade de moedas do jogador
    * */
    public int getCoins(){
        return this.coins;
    }

    /**
     * muda o numero de moedas do jogador para "coins"
    * */
    public void setCoins(int coins){
        this.coins = coins;
    }

    /**
     * Diminui o a quantidade de moedas do jogador em "coins"
    * */
    public void giveCoins(int coins){
        this.coins -= coins;
    }

    /**
     * Aumenta o a quantidade de moedas do jogador em "coins"
     * */
    public void gainCoins(int coins){
        this.coins += coins;
    }

    /**
     * Gera uma nova mão com as cartas de "hand" para o jogador
     * @throws NullPointerException
     * */
    public void newHand(Card[] hand) throws NullPointerException {
        this.hand = hand;
    }

    /**
     * Retorna a o vetor de cartas correspondente à mão atual do jogador
    * @return mão atual do jogador
     * */
    public Card[] getHand() {
        return hand;
    }

    /**
     * Muda as cartas solicitadas pelo jogador.
     * Retorna nulo caso "which" seja inválida
     * @throws NullPointerException
    * */
    public void changeCards(String which, Deck deck) throws NullPointerException {

        String[] ids = which.split(" ");

        for(int i = 0; i < ids.length ; i++ ) {
            int aux = Integer.parseInt(ids[i]);
            this.hand[aux-1] = deck.giveCard();
        }

    }

    /**
     * Coloca novas cartas na mão do jogador
     * @return none
     * */
    public void setNewHand(Card [] newHand){
        this.hand = newHand;
    }

    /**
     * Retorna uma string contendo ASCII ART da mão atual do jogador
     * @return String asciiArt
    * */
    @Override
    public String toString() {

        String saida = "";

        //inicializa a string com a indexação das cartas
        for(int i = 1; i <= 5; i++) {
            saida+="     "+i+"       ";
        }

        saida += "\n";

        //formata a saída das cartas linha por linha
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 5; j++ ) {
                saida+= hand[j].getRowCard(i) + "  ";
            }
            saida+="\n";
        }
        return saida;
    }
}
