/**
 * Esta classe foi criada para fazer o controle das telas de jogo
 * */
public class ScreenController {
    private FirstScreen firstScreen;
    private PlayScreen secondScreen;
    private WinScreen thirdScreen;

    /**
     * Configura a disposição das telas inicias
     * */
    public ScreenController() throws  Exception{
        this.secondScreen = new PlayScreen(this);
        this.firstScreen = new FirstScreen(this);

        this.firstScreen.setVisible(true);
        this.secondScreen.setVisible(false);
    }

    /**
     * Mostra a tela inicial
    * */
    public void showFirstScreen(){
        this.firstScreen.setVisible(true);
        this.secondScreen.setVisible(false);
        if(thirdScreen != null)
            this.thirdScreen.setVisible(false);
    }

    /**
     * Mostra a tela de gameplay
     * */
    public void showSecondScreen(){
        this.firstScreen.setVisible(false);
        this.secondScreen.setVisible(true);
        if(this.thirdScreen != null)
            this.thirdScreen.setVisible(false);
    }

    /**
     * Mostra a tela de premiação
     * */
    public void showThirdScreen(String background) throws Exception{
        this.firstScreen.setVisible(false);
        this.secondScreen.setVisible(false);
        this.thirdScreen = new WinScreen(this, background);
        this.thirdScreen.setVisible(true);
    }

    /**
     * @Return referência para a primeira tela
     * */
    public FirstScreen getFirstScreen() {
        return firstScreen;
    }

    /**
     * @Return referência para a segunda tela
     * */
    public PlayScreen getSecondScreen() {
        return secondScreen;
    }

    /**
     * @Return referência para a terceira tela
     * */
    public WinScreen getThirdScreen() {
        return thirdScreen;
    }

    /**
     * Reinicia o jogo
     * */
    public void newGame(){
        try {
            new ScreenController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
