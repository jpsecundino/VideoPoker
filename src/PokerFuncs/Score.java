package PokerFuncs;

/**
 * Esta classe foi desenhada para facilitar o cálculo da pontuação obtida pelo jogador.
 *
 * @author João Pedro Almeida Santos Secundino
 *
 */
public class Score {

    private String[] ranks = {"2", "3","4","5","6","7","8","9","10","J","Q","K","A"};

    //vetor utilizado para checar a distribuição das cartas na mão do jogador
    private Boolean[] handCards = {
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
    };

    /**
     * @return indice do vetor "ranks" correspondente à carta indicada na string s
     * @throws NullPointerException
     * */
    private int findIndex(String s) throws NullPointerException {
        for (int i = 0; i < 13; i++){
            if(s.equals(this.ranks[i]))
                return i;
        }
        return -1;
    }

    /**
     * Recolhe dados do vetor de cartas correspondente à mão do jogador
     * @throws NullPointerException
    * */
    private void checkHand(Card[] hand) throws NullPointerException {
        int j = 0;
        for (int i = 0; i < 5; i++) {
            j = findIndex(hand[i].getRank());
            handCards[j] = true;
        }
    }

    /**
     * Verifica se existe uma sequencia na mão do jogador
     * @return veracidade da sequência
     * @throws NullPointerException
     * */
    private Boolean isFollowed(Card[] aux) throws NullPointerException{
        Boolean flag = false;
        int trueCount = 0;

        for (int i = 0; i < 13; i++){

            //verifica se a carta está na mão do jogador
            if(this.handCards[i] == true){
                flag = true;
                trueCount++;
            }

            //verifica se as cartas não são consecutivas
            if(flag == true && this.handCards[i] == false){
                return false;
            }
            //verifica se já conseguimos uma sequência de 5 cartas
            if (trueCount == 5)
                break;

        }
        return flag;
    }
    /**
     * Verifica se todas as cartas da mão são do mesmo naipe
     * @return veracidade da igualdade
     * @throws NullPointerException
     * */
    private Boolean isSameSuit(Card[] aux) throws NullPointerException{
        for (int i = 1; i < 5; i++){
            if(aux[i].getSuit() != aux[i-1].getSuit())
                return false;
        }
        return true;
    }

    /**
     *Conta o numero de ocorrências de cada carta na mão do jogador
     *  @return vetor contendo os numeros de ocorrêcia
     * @throws NullPointerException
     * */
    private int[] countCards(Card[] hand) throws NullPointerException{
        int[] count = {0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < 5; i++) {
            int index = this.findIndex(hand[i].getRank());
            count[index]++;
        }

        return count;
    }

    /**
    *Verifica se a mão do jogador configura um Royal Straight Flush
     *  @return validade da jogada
     * @throws NullPointerException
    * */
    private Boolean isRoyalStraightFlush(Card[] hand) throws NullPointerException {

        if(isSameSuit(hand) && isFollowed(hand)){
            for (int i = 8; i<13; i++){
                if(!this.handCards[i]){
                    return false;
                }
            }

            return true;
        }else{
            return false;
        }

    }

    /**
     *Verifica se a mao do jogador configura um Straight Flush
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isStraightFlush(Card[] hand) throws  NullPointerException{

        if(isSameSuit(hand) && isFollowed(hand)){
          return true;
        }else{
            return false;
        }
    }

    /**
     *Verifica se a mao do jogador configura uma Quadra
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isFourOfAKind(Card[] hand) throws NullPointerException{

        int[] count = this.countCards(hand);

        for (int i = 0; i < 13; i++){
            if(count[i] == 4){
                return true;
            }

        }

        return false;

    }

    /**
     *Verifica se a mao do jogador configura uma Full Hand
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isFullHand(Card[] hand) throws NullPointerException{
        int[] count = this.countCards(hand);
        Boolean threeOfAKind = false;
        Boolean pair = false;

        for (int i = 0; i < 13; i++){
            if(count[i] == 3){
                threeOfAKind = true;
            }
            if(count[i] == 2){
                pair = true;
            }

        }

        return threeOfAKind && pair;
    }

    /**
     *Verifica se a mao do jogador configura um Flush
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isFlush (Card[] hand) throws NullPointerException{
        return isSameSuit(hand) && !isFollowed(hand);
    }

    /**
     *Verifica se a mao do jogador configura um Straight
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isStraight(Card[] hand) throws NullPointerException{
        return isFollowed(hand) && !isSameSuit(hand);
    }

    /**
     *Verifica se a mao do jogador configura uma Trinca
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isThreeOfAKind(Card[] hand) throws NullPointerException{
        int[] count = this.countCards(hand);

        for (int i = 0; i < 13; i++){
            if(count[i] == 3){
                return true;
            }
        }

        return false;
    }

    /**
     *Verifica se a mao do jogador possui dois Pares
     *  @return validade da jogada
     * @throws NullPointerException
     * */
    private Boolean isTwoPairs(Card[] hand) throws NullPointerException{
        int[] count = this.countCards(hand);

        Boolean firstPair = false;
        Boolean secondPair = false;


        for (int i = 0; i < 13; i++){
            if(count[i] == 2){
                if(!(firstPair)){ //se o primeiro par não foi encontrado
                    firstPair = true;
                }else{
                    secondPair = true;
                    break;
                }
            }
        }

        return firstPair && secondPair;
    }

    /**
     *Calcula a pontuação da mão do jogador e retorna o seu multiplicador
     *  @return multiplicador da jogada
     * @throws NullPointerException
     * */
    private int totalScore(Card[] hand) throws NullPointerException{

        checkHand(hand);

        if(isRoyalStraightFlush(hand))
            return 200;
        if(isStraightFlush(hand))
            return 100;
        if(isFourOfAKind(hand))
            return 50;
        if(isFullHand(hand))
            return 20;
        if(isFlush(hand))
            return 10;
        if (isStraight(hand))
            return 5;
        if(isThreeOfAKind(hand))
            return 2;
        if(isTwoPairs(hand))
            return 1;
        return 0;

    }

    /**
     *Retorna a classificação da mao do jogador
     *  @return nome da jogada
     * */
    public String handClassification(Card[] hand){

        checkHand(hand);

        if(isRoyalStraightFlush(hand))
            return "royalStraightFlush";
        if(isStraightFlush(hand))
            return "straightFlush";
        if(isFourOfAKind(hand))
            return "fourOfAKind";
        if(isFullHand(hand))
            return "fullHand";
        if(isFlush(hand))
            return "flush";
        if (isStraight(hand))
            return "straight";
        if(isThreeOfAKind(hand))
            return "threeOfAKind";
        if(isTwoPairs(hand))
            return "twoPairs";
        return "none";

    }

    /**
     *Retorna o prêmio do jogador de acordo com a configuração da sua mão
     *  @return multiplicadorDeJogada * aposta
     * @throws NullPointerException
     * */
    public int showScore(Card[] hand, int bet) throws NullPointerException{
        return totalScore(hand)*bet;
    }

}
