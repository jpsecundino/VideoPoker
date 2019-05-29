import PokerFuncs.Card;
import PokerFuncs.Deck;
import PokerFuncs.Player;
import PokerFuncs.Score;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/**
 * Esta classe foi criada para representar a segunda tela do jogo: a de gameplay
 * */
public class PlayScreen extends JFrame {
    //declarando todos os componentes da classe
    private Font projectFont= new Font("Arcade Classic", Font.BOLD, 20);
    private URL urlToImageBackground = this.getClass().getResource("Images/Backgrounds/defaultBackground.jpg"),
                urlToTitleBackground = this.getClass().getResource("Images/Backgrounds/titleBackground.png");

    private Image backgroundImage, titleIcon;
    private ImageIcon titleBackgroundImage;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ScreenController screenController = null;
    private JButton backToFirstScreenButton, selectBet, changeCardsButton, getPrizeButton;
    private JPanel centerPanel, southPanel, eastPanel, westPanel, northPanel;
    private JTextField betField;
    private JLabel roundCount, moneyCount, titleBackgroundLabel, messagesDialog, messagesDialogImage;
    private JCheckBox cardsArray[];
    private Player player;
    private Deck deck;
    private Score score;
    private Icon unselectedBackCardIcon = new ImageIcon("src/Images/Cards/fundoAzul.png"),
                 selectedBackCardIcon = new ImageIcon("src/Images/Cards/fundoVerde.png"),
                messageDialogIcon = new ImageIcon("src/Images/Sprites/Poker-icon.png");
    private int playerBet, actualRound;

    /**
     * Construtor: faz todas as atribuições necessárias para uma boa experiência visual nessa tela
     *
     *  * */
    public PlayScreen(ScreenController sc) throws Exception {
        //cria as telas
        createScreen(sc);
        //cria os paineis da tela
        createPanels();
        //cria o jogo
        createGame();

        //painel configs
        centerPanel.setOpaque(false);

        //set backgrounds
        setBackgrounds();

        //set north panel
        setNorthPanel();

        //set center panel
        setCenterPanel();

        //set east panel
        setEastPanel(sc);

        //set south panel
        setSouthPanel();

        //set screen size
        setScreenSize();
    }
    /**
     * Define os tamanhos dos paineis adicionados ao Jframe atual
     * */
    private void setScreenSize() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.add(BorderLayout.SOUTH, southPanel);
        this.add(BorderLayout.NORTH, northPanel);
        this.add(BorderLayout.EAST, eastPanel);
        this.add(BorderLayout.CENTER, centerPanel);
    }
    /**
     * Define as configurações do painel do sul
     * */
    private void setSouthPanel() {
        GridLayout southPanelLayout = new GridLayout();
        southPanel.setLayout(southPanelLayout);
        roundCount = new JLabel("Round: "+ actualRound +"/2");
        moneyCount = new JLabel("Moedas:" + player.getCoins());
        southPanel.add(roundCount);
        southPanel.add(moneyCount);
        southPanel.setSize(new Dimension(screenSize.width,100));
    }

    /**
     * Define as configurações do painel do leste
     * */
    private void setEastPanel(ScreenController sc) {
        //cria e define os componentes necessários
        GridLayout optionsLayout = new GridLayout(7, 1, 0, 20);
        eastPanel.setLayout(optionsLayout);
        eastPanel.setOpaque(false);
        selectBet = new JButton();
        changeCardsButton = new JButton();
        getPrizeButton = new JButton();
        backToFirstScreenButton = new JButton("Voltar ao menu principal");

        //adiciona os botões
        selectBet.setText("Apostar");
        selectBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent payBet) {

                try {
                    playerBet = Integer.parseInt(betField.getText());
                    if(playerBet > player.getCoins() || playerBet <= 0 ) {
                        if(playerBet > player.getCoins()) {
                            messagesDialog.setText("Não aposte mais do que tem! Tente novamente.");
                        }else{
                            messagesDialog.setText("Aposte um valor válido");
                        }
                        return;
                    }
                }catch(NumberFormatException n){
                    messagesDialog.setText("Digite um valor válido!");
                    n.getSuppressed();
                    return;
                }

                player.giveCoins(Integer.parseInt(betField.getText()));

                selectBet.setVisible(false);

                moneyCount.setText("Moedas:" + String.valueOf(player.getCoins()));

                changeCardsButton.setEnabled(true);
                getPrizeButton.setEnabled(true);
                betField.setVisible(false);

                messagesDialog.setText("Selecione as cartas que deseja trocar ou finalize a rodada para pegar o seu prêmio.");

                player.newHand(deck.giveHand());

                for (int i = 0; i < 5; i++){
                    Card c = player.getHand()[i];
                    cardsArray[i].setIcon(new ImageIcon(  "src/Images/Cards/" + c.getSuit() + c.getRank() + ".png"));
                    cardsArray[i].setSelected(false);
                }

            }
        });

        changeCardsButton.setEnabled(false);
        changeCardsButton.setText("Trocar cartas selecionadas");
        changeCardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String wasChoosen = new String();

                for (int i = 0; i < 5; i++) {
                    if (cardsArray[i].isSelected()) {
                        wasChoosen += String.valueOf(i + 1) + " ";
                    }
                }

                if(wasChoosen.length() > 0){
                    player.changeCards(wasChoosen, deck);
                }
                for (int i = 0; i < 5; i++){
                    Card c = player.getHand()[i];
                    //change image of selected cards
                    if(cardsArray[i].isSelected()) {
                        cardsArray[i].setIcon(new ImageIcon("src/Images/Cards/" + c.getSuit() + c.getRank() + ".png"));
                    }
                    cardsArray[i].setSelected(false);
                }

                actualRound++;
                messagesDialog.setText("Deseja trocar mais alguma carta ou prefere finalizar a rodada?");

                if(actualRound > 2){
                    messagesDialog.setText("Finalize a rodada para ver oquanto você ganhou.");
                    actualRound = 1;
                    changeCardsButton.setVisible(false);
                }

                roundCount.setText("Round:" + actualRound + "/2");
            }
        });

        getPrizeButton.setEnabled(false);
        getPrizeButton.setText("Finalizar rodada");
        getPrizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int playerScore = score.showScore(player.getHand(), playerBet);
                player.gainCoins(playerScore);
                messagesDialog.setText("Moedas:" + player.getCoins());
                try {
                    if (playerScore >= 0 && player.getCoins() > 0) {
                        sc.showThirdScreen(score.handClassification(player.getHand()));
                    } else if (player.getCoins() == 0) {
                        sc.showThirdScreen("looser");
                    }
                }catch (Exception e){}

            }
        });

        betField = new JTextField();

        this.backToFirstScreenButton.setBounds(screenSize.width/2 - 150,screenSize.height/2 + 200,300,40);
        this.backToFirstScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sc.showFirstScreen();
            }
        });

        //adiciona os elementos ao painel leste
        eastPanel.add(betField);
        eastPanel.add(selectBet);
        eastPanel.add(changeCardsButton);
        eastPanel.add(getPrizeButton);
        eastPanel.add(backToFirstScreenButton);
    }
    /**
     * Define as configurações do painel central
     * */
    private void setCenterPanel() {
        //cria um layout para mostrar as cartas do jogador
        GridLayout cardsLayout = new GridLayout(1, 5, 0, 0);
        centerPanel.setLayout(cardsLayout);
        //adiciona as cartas do jogador ao painel
        for (int i = 0; i < 5; i ++ ){
            cardsArray[i] = new JCheckBox();
            cardsArray[i].setIcon(unselectedBackCardIcon);
            cardsArray[i].setSelectedIcon(unselectedBackCardIcon);
            cardsArray[i].setOpaque(false);
            centerPanel.add(cardsArray[i]);
        }
    }
    /**
     * Define as configurações do painel norte
     * */
    private void setNorthPanel() {
        northPanel.setPreferredSize(new Dimension(screenSize.width/5,screenSize.height/2));
        titleBackgroundLabel = new JLabel(titleBackgroundImage, SwingConstants.CENTER);
        setInitialMessages();
        northPanel.setOpaque(false);
        BorderLayout titlePanel = new BorderLayout();
        northPanel.setLayout(titlePanel);
        northPanel.add(BorderLayout.NORTH,titleBackgroundLabel);
        northPanel.add(BorderLayout.SOUTH, messagesDialog);
    }
    /**
     * Define as configurações da caixa de mensagem
     * */
    private void setInitialMessages() {
        messagesDialog = new JLabel("Digite a sua aposta ao lado", messageDialogIcon, JLabel.CENTER);
        messagesDialog.setFont(projectFont);
        messagesDialog.setForeground(Color.WHITE);
        messagesDialog.setPreferredSize(new Dimension(screenSize.width, 130));
        messagesDialog.setAlignmentY(Component.LEFT_ALIGNMENT);
        messagesDialog.setVerticalTextPosition(SwingConstants.CENTER);
        messagesDialog.setOpaque(false);
    }

    /**
     * Define os backgrounds do JFrame
     * */
    private void setBackgrounds() throws IOException {
        backgroundImage = ImageIO.read(urlToImageBackground);
        this.setContentPane(new JPanel(new BorderLayout()){
            @Override public void paintComponent(Graphics g){
                g.drawImage(backgroundImage.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_DEFAULT),0,0,null);
            }
        });
        titleBackgroundImage = new ImageIcon("src/Images/Backgrounds/titleBackground.png");
    }

    /**
     * Cria os componentes do jogo
    * */
    private void createGame() throws InterruptedException {
        player = new Player();
        deck = new Deck();
        score = new Score();
    }

    /**
     * Cria os paineis do Jframe
     * */
    private void createPanels() {
        centerPanel = new JPanel();
        southPanel = new JPanel();
        eastPanel = new JPanel();
        westPanel = new JPanel();
        northPanel = new JPanel();
    }

    /**
     * Cria a tela principal
    * */
    private void createScreen(ScreenController sc) {
        cardsArray = new JCheckBox[5];
        this.setTitle("VideoPoker 5000");
        this.screenController = sc;
        this.setLayout( new BorderLayout());
        this.actualRound = 1;
    }

    /**
     * Inicia uma nova rodada
     * */
    public void newRound() throws Exception{
        deck = new Deck();
        player.setNewHand(deck.giveHand());
        actualRound = 1;
        player.newHand(deck.giveHand());
        betField.setVisible(true);
        selectBet.setVisible(true);
        changeCardsButton.setVisible(true);
        changeCardsButton.setEnabled(false);
        getPrizeButton.setEnabled(false);
        messagesDialog.setText("Digite a sua ao lado");

        for(JCheckBox i : this.cardsArray){
            i.setIcon(unselectedBackCardIcon);
        }
    }


}
