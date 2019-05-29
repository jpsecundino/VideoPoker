package PokerFuncs;

/**
 * Esta classe foi desenhada para simplificar o manuseio do monte de cartas.
 *
 * @author João Pedro Almeida Santos Secundino
 *
 */
public class Deck {

    private String[] numbers = {"2", "3","4","5","6","7","8","9","10","J","Q","K","A"};
    private char[] suits = {'♠', '♦', '♥', '♣'};

    private Boolean[] cardsAvailable; // vetor que guarda a disponibilidade das cartas

    private Random r;

    private Card[] pack = new Card[52];

    /**
     * Inicializa o monte da cartas. Todas as cartas são adicionadas ao baralho
     * @throws InterruptedException
    * */
    public Deck() throws InterruptedException{

        r = new Random();

        this.cardsAvailable = new Boolean[52];

        //torna todas as cartas disponíveis
        for (int i = 0; i < 52; i++){
            this.cardsAvailable[i] = true;
        }
        //cria todas as cartas do baralho
        int pos = 0;
        for (int i = 0; i < 13; i++){
            for (int j = 0 ; j < 4; j++ ){
                this.pack[pos++] = new Card(this.numbers[i], this.suits[j]);
            }
        }

    }

    /**
     * Função utilizada para buscar uma carta no baralho
     * @return indice da carta no baralho.
     * Não há perigo de a carta não existir.
    * */
    private int findCard(Card c){
        for (int i = 0; i < 52; i++){
            Card aux = pack[i];
            if(c.getRank() == aux.getRank() && c.getSuit() == aux.getSuit()){
                return i;
            }
        }

        //O retorno -1 é apena para evitar a falta de um retorno na função
        return -1;
    }
    /**
     * Checa se a carta indicada no argumento está disponível para ser utilizada
     * @return disponibilidade da carta
     * */
    private Boolean checkAvailability(Card c){
        int i = findCard(c);

        if(cardsAvailable[i] == true){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Retorna uma carta aleatória disponível no baralho
     * @return objeto do tipo Card inicializado
    * */
    public Card giveCard() {

        int number = r.getIntRand(13);
        int suit = r.getIntRand(4);

        Card aux = new Card( this.numbers[number], this.suits[suit] );

        if( checkAvailability(aux) ){
            this.cardsAvailable[this.findCard(aux)] = false;
            return aux;

        }else{
            return giveCard();
        }

    }

    /**
     * Retorna uma mão (5 cartas aleatórias) completa
     * @return vetor de cartas aleatórias
     * */
    public Card[] giveHand(){

        Card[] hand = new Card[5];

        for (int i = 0; i < 5; i++){
            hand[i] = giveCard();
        }

        return hand;
    }


}
