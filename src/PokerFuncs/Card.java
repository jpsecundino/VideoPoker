package PokerFuncs;

/**
 * Esta classe foi desenhada para facilitar o manuseio das cartas
 * no jogo.
 *
 * @author João Pedro Almeida Santos Secundino
 *
 */
public class Card {


    private String rank;
    private char suit;
    /*
    * Template utilizado para facilitar a impressão das cartas
    */
    private String template[] =         {
                    "┌─────────┐",
                    "│x.       │",
                    "│         │",
                    "│         │",
                    "│    x    │",
                    "│         │",
                    "│         │",
                    "│       x.│",
                    "└─────────┘"

    };

    /**
     * Recebe o numero da linha da matriz do template a ser retornada.
     * Não há perigo de que o n seja maior que o numero de linhas
     * @return string contendo a linha solicitada
     */
    public String getRowCard ( int n ){

        String auxString = this.template[n];
        if (n == 1 || n == 7) {
            if (this.rank.equals("10")) {
                auxString = auxString.replace('x', this.rank.charAt(0));
                auxString = auxString.replace('.', this.rank.charAt(1));
            } else {
                if (n == 7) {
                    auxString = auxString.replace('x', ' ');
                    auxString = auxString.replace('.', this.rank.charAt(0));
                } else {
                    auxString = auxString.replace('.', ' ');
                    auxString = auxString.replace('x', this.rank.charAt(0));
                }
            }
        } else if (n == 4) {
            auxString = auxString.replace('x', this.suit);
        }

        return auxString;

    }

    /**
     * Construtor padrão da classe
     * Retorna uma carta inicializada com "A" e '♠'
    * */
    public Card() {
        this.rank = "A";
        this.suit = '♠';

    }

    /**
     * Constroi uma carta incializada com numero = rank e naipe = suit
     * */
    public Card(String rank, char suit) {
        this.rank = rank;
        this.suit = suit;

    }

    /**
     * Getter que retorna o numero da carta
     * @return  numero da carta
    * */
    public String getRank() {
        return this.rank;
    }

    /**
     * Setter para o numero da carta
    * */
    public void setRank(String number) {
        this.rank = number;
    }

    /**
     * Getter que retorna o naipe da carta
     * @return naipe da carta
    * */
    public char getSuit() {
        return this.suit;
    }

    /**
     * Setter que muda o naipe da carta
    * */
    public void setSuit(char suit) {
        this.suit = suit;
    }

    /**
     * Retorna um ASCII ART da carta
     * @return string asciiArt da carta
    * */
    @Override
    public String toString() {
        String returnedString = new String();

        for (int i = 0; i < 9; i++){

            returnedString+=this.getRowCard(i);

            returnedString+='\n';

        }
        return returnedString;
    }


}
