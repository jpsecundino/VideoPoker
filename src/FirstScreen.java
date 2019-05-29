import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
/**
 * Essa classe foi feita para representar visualmente a primeira tela do jogo: a tela inicial
 * @Authors: João Pedro Almeida Santos Secundino
 * */
public class FirstScreen extends JFrame {
    private URL urlToImage = this.getClass().getResource("Images/Backgrounds/BeginBackground.jpg");
    private Image background;
    private JButton gameInit = new JButton("Jogar");
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ScreenController screenController = null;

    /**
    *Construtor da classe: esse método contruirá toda a interface da primeira tela
    * */
    public FirstScreen(ScreenController sc) throws Exception{

        this.setTitle("VideoPoker 5000");
        this.screenController = sc;

        //coloca  um novo background da tela
        background = ImageIO.read(urlToImage);
        this.setContentPane(new JPanel(new BorderLayout()){
            @Override public void paintComponent(Graphics g){
                g.drawImage(background.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_DEFAULT),0,0,null);
            }
        });
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);//coloca a tela no tamanho máximo do monitor

        //coloca o botão de iniciar jogo na tela
        this.gameInit.setBounds(screenSize.width/2 - 150,screenSize.height/2 + 200,300,40);
        //atribui o evento de clique ao botão
        this.gameInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sc.showSecondScreen();
            }
        });
        //adiciona o botão à tela
        this.add(gameInit);
        this.setLayout(null);

    }


}
