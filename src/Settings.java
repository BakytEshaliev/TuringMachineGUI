import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {
    private JTextField qTextField;
    private JTextField aTextField;
    private JTextField configTextField;

    private JTextArea programTextArea;

    private JLabel errorLabel;

    private TuringMachine tm;
    private GUI main;

    public Settings(TuringMachine t, GUI main){
        tm = t;
        this.main = main;

        this.setSize(500,700);
        this.setTitle("Settings");
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,
                BoxLayout.Y_AXIS));

        errorLabel = new JLabel();
        errorLabel.setFont(new Font("Monospaced", Font.PLAIN,20));
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        JLabel qLabel = new JLabel("Q (qz and q0 are constants, write only chars with space between) :");
        panel.add(qLabel);

        qTextField = new JTextField(25);
        panel.add(qTextField);

        JButton qBtn = new JButton("Change");
        qBtn.addActionListener(e -> qBtnListener());
        panel.add(qBtn);

        panel.add(new JSeparator(SwingConstants.VERTICAL));

        JLabel aLabel = new JLabel("A (a0 is constant, write only chars with space between):");
        panel.add(aLabel);

        aTextField = new JTextField(25);
        panel.add(aTextField);

        JButton aBtn = new JButton("Change");
        aBtn.addActionListener(e -> aBtnListener());
        panel.add(aBtn);

        JLabel configLabel = new JLabel("Configuration (in format \"Cq0aB\" where C is left part of tape, q0 is current state, a is current symbol and B is right part of tape):");
        panel.add(configLabel);

        configTextField = new JTextField(25);
        panel.add(configTextField);

        JButton configBtn = new JButton("Change");
        configBtn.addActionListener(e -> configBtnListener());
        panel.add(configBtn);

        JPanel programPanel = new JPanel();
        programPanel.setLayout(new BoxLayout(programPanel,
                BoxLayout.Y_AXIS));;

        JLabel programLabel = new JLabel("Program (in format \"q02 -> q13R;\" where q0 is current state, 2 is current symbol, q1 is new state, 3 is new symbol, R is direction (it could be L or N) and ; is separator between lines):");
        programPanel.add(programLabel);

        programTextArea = new JTextArea();
        programPanel.add(programTextArea);

        JButton programBtn = new JButton("Change");
        programBtn.addActionListener(e -> programBtnListener());
        programPanel.add(programBtn);


        this.add(panel, BorderLayout.NORTH);
        this.add(programPanel, BorderLayout.WEST);

        this.setVisible(true);

        UpdateDisplay();
    }

    private void programBtnListener() {
        String text = programTextArea.getText().trim();
        if (!tm.isValidRules(text)) errorLabel.setText("Invalid input!!!");
        else {
            tm.setProgram(text);
            errorLabel.setText("");
        }
        UpdateDisplay();

    }

    private void configBtnListener() {
        String config = "_" + configTextField.getText() + "_";
        if (!tm.isValidConfig(config)) errorLabel.setText("Invalid input!!!");
        else {
            tm.setConfiguration(config);
            errorLabel.setText("");
        }
        UpdateDisplay();
    }

    private void aBtnListener() {
        String A = aTextField.getText();
        if (!tm.isValidSet(A)) errorLabel.setText("Invalid input!!!");
        else {
            tm.setA(A);
            errorLabel.setText("");
        }
        UpdateDisplay();
    }

    private void qBtnListener() {
        String Q = qTextField.getText();
        if (!tm.isValidSet(Q)) errorLabel.setText("Invalid input!!!");
        else {
            tm.setQ(Q);
            errorLabel.setText("");
        }
        UpdateDisplay();
    }

    private void UpdateDisplay(){
        aTextField.setText(tm.getA());
        qTextField.setText(tm.getQ());
        configTextField.setText(tm.getConfigurationStr().replace("_",""));
        programTextArea.setText(tm.getProgram());
        main.UpdateDisplay();
    }
}
