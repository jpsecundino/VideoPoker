import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/**
 * Esta classe foi criada para representar a terceira tela do jogo: a de premiação
 * */
public class WinScreen extends JFrame {
    private JLabel messageDialog;
    private URL urlToImage;
    private Image background;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(),
            buttonSize = new Dimension(500, 30);
    private ScreenController screenController;
    private JButton playAgain, getOut;
    private JPanel mainPanel;

    /**
     * Construtor
     * */
    public WinScreen(ScreenController sc, String background) throws IOException {
        screenController = sc;
        this.setTitle("VideoPoker 500");
        //muda o background
        setBackground(background);

        // setting buttons
        playAgain = new JButton("Jogar novamente");
        playAgain.setPreferredSize(buttonSize);
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    sc.getSecondScreen().newRound();
                    sc.showSecondScreen();
                }catch(Exception e){
                    System.out.println("Problema no bootao de playagain");
                }
            }
        });

        getOut = new JButton("Voltar ao menu");
        getOut.setPreferredSize(buttonSize);
        getOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                try{
                   sc.newGame();
                }catch (Exception e){}
            }
        });

        //criando um painel para adicionar os botoes
        mainPanel = new JPanel();
        mainPanel.add(BorderLayout.SOUTH,playAgain);
        mainPanel.add(BorderLayout.NORTH,getOut);
        mainPanel.setOpaque(false);

        //adicionando o painel ao jframe
        this.add(mainPanel);
    }

    /**
     * Muda o background do jframe
     * */
    public void setBackground(String handClassification) throws IOException {
        urlToImage = this.getClass().getResource("Images/Backgrounds/" + handClassification+".png");

        background = ImageIO.read(urlToImage);

        this.setLayout(null);

        this.setSize(new Dimension(screenSize.width,screenSize.height));

        this.setContentPane(new JPanel(new BorderLayout()){
            @Override public void paintComponent(Graphics g){
                g.drawImage(background.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH),0,0,null);
                for (Component c : this.getComponents())
                    c.invalidate();
            }
        });
    }
}
