import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public static void main(String [] args)
    {
        new GUI();
    }

    private JLabel lblTapeLeft;
    private JLabel lblTapeCurrent;
    private JLabel lblTapeRight;
    private JLabel lblState;
    private JLabel lblQ;
    private JLabel lblA;
    private JLabel lblQ0;
    private JLabel lblQz;
    private JLabel lblA0;
    private JLabel lblProgram;
    private JLabel lblCurrentQ;

    private JButton btnGo;
    private JButton btnStep;
    private JButton btnSettings;

    private JEditorPane paneProgram;
    private JEditorPane paneRules;

    private TuringMachine tm;

    public GUI()
    {
        tm = new TuringMachine();

        this.setSize(1440,900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Turing Machine");
        this.setLayout(new BorderLayout());

        JPanel panelLine = new JPanel();

        lblTapeLeft = new JLabel("_");
        lblTapeCurrent = new JLabel("_");
        lblTapeRight = new JLabel("_");
        lblTapeLeft.setFont(new Font("Monospaced", Font.PLAIN,50));
        lblTapeCurrent.setFont(new Font("Monospaced", Font.BOLD,50));
        lblTapeRight.setFont(new Font("Monospaced", Font.PLAIN,50));
        lblTapeCurrent.setForeground(Color.RED);
        panelLine.add(lblTapeLeft);
        panelLine.add(lblTapeCurrent);
        panelLine.add(lblTapeRight);


        JPanel panelBtns = new JPanel();

        btnGo = new JButton("Start");
        btnGo.addActionListener(e -> btnGoClick() );
        panelBtns.add(btnGo);

        btnStep = new JButton("Step");
        btnStep.addActionListener(e -> btnStepClick() );
        panelBtns.add(btnStep);

        btnSettings = new JButton("Settings");
        btnSettings.addActionListener(e -> btnSettingsClick());
        panelBtns.add(btnSettings);


        JPanel panelSet = new JPanel();
        panelSet.setLayout(new BoxLayout(panelSet, BoxLayout.Y_AXIS));


        lblState = new JLabel("");
        lblState.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblState);

        lblQ = new JLabel("");
        lblQ.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblQ);

        lblA = new JLabel("");
        lblA.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblA);

        lblQ0 = new JLabel("q0: 0");
        lblQ0.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblQ0);

        lblQz = new JLabel("qz: z");
        lblQz.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblQz);

        lblA0 = new JLabel("a0: _");
        lblA0.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblA0);

        lblCurrentQ = new JLabel("Current Q: 0");
        lblCurrentQ.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblCurrentQ);

        lblProgram = new JLabel("Program: ");
        lblProgram.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelSet.add(lblProgram);

        paneProgram = new JEditorPane();
        paneProgram.setEditable(false);
        paneProgram.setAlignmentX(-100);
        panelSet.add(paneProgram);

        JPanel panelRules = new JPanel();
        panelRules.setLayout(new BoxLayout(panelRules, BoxLayout.Y_AXIS));

        JLabel lblRules = new JLabel("Progress:");
        lblRules.setFont(new Font("Monospaced", Font.PLAIN,30));
        panelRules.add(lblRules);

        paneRules = new JEditorPane();
        paneRules.setEditable(false);
        panelRules.add(paneRules);

        JButton clearProgressBtn = new JButton("Clear");
        clearProgressBtn.addActionListener(e -> clearProgress());
        panelRules.add(clearProgressBtn);


        this.add(panelLine, BorderLayout.NORTH);
        this.add(panelBtns, BorderLayout.SOUTH);
        this.add(panelSet, BorderLayout.WEST);
        this.add(panelRules, BorderLayout.EAST);

        this.setVisible(true);

        UpdateDisplay();

    }

    private void clearProgress() {
        paneRules.setText("");
    }

    private void btnSettingsClick() {
        new Settings(tm, this);
    }

    private void btnGoClick() {
        String result = "";
        String errorStr = "Can't find rule for this state";
        String stopStr = "Program stopped";
        while (!result.equals(errorStr) && !result.equals(stopStr)) {
            result = tm.step();
            setProgress(result);
            setLine();
            lblCurrentQ.setText("Current q: " + tm.getCurrQ());
        }
    }

    private void btnStepClick() {
        setProgress(tm.step());
        setLine();
        lblCurrentQ.setText("Current q: " + tm.getCurrQ());
    }

    private void setProgress(String line){
        paneRules.setText(paneRules.getText() + line + "\n");
    }

    public void UpdateDisplay()
    {
        setLine();
        lblQ.setText("Q: " + tm.getQ());
        lblA.setText("A: " + tm.getA());
        lblCurrentQ.setText("Current q: " + tm.getCurrQ());
        paneProgram.setText(tm.getProgram());
    }

    private void setLine(){
        String[] configArr = tm.getConfiguration();
        lblTapeLeft.setText(configArr[0]);
        lblTapeCurrent.setText(configArr[1]);
        lblTapeRight.setText(configArr[2]);
    }
}