
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TuringMachine {
    private Set<Character> Q;
    private Set<Character> A;
    private HashMap<String, String> program;

    private final char q0 = '0';
    private final char qz = 'z';
    private final char a0 = '_';

    private char currQ;
    private char currA;
    private String C;
    private String B;

    public TuringMachine(){
        Q = new HashSet<>();
        Q.add(q0);
        Q.add(qz);
        currQ = q0;
        C = "_";
        currA = a0;
        B = "_";
        A = new HashSet<>();
        A.add(a0);
        program = new HashMap<>();
    }

    public String step(){
        if (currQ == qz) return "Program stopped";

        String currentState = getCurrentState();
        String rule = program.get(currentState);

        if (currentState == null || rule == null) return "Can't find rule for this state";
        doRule(rule);
        return currentState + " -> " + rule + ";";
    }

    private void doRule(String rule) {
        currQ = rule.charAt(1);
        currA = rule.charAt(2);
        char direction = rule.charAt(3);
        setConfiguration(direction);
    }

    private void setConfiguration(char direction) {
        switch (direction){
            case 'L':
                B = currA + B;
                currA = C.charAt(C.length() - 1);
                C = C.substring(0, C.length() - 1);
                break;
            case 'R':
                C += currA;
                currA = B.charAt(0);
                B = B.substring(1);
        }
    }

    public String getQ(){
        StringBuilder sb = new StringBuilder();
        for (char c : Q){
            sb.append(c).append(" ");
        }
        return sb.toString();
    }

    private String getCurrentState(){
        String result = "q" + currQ + currA;
        return result;
    }

    public String getA() {
        StringBuilder sb = new StringBuilder();
        for (char c : A){
            sb.append(c).append(" ");
        }
        return sb.toString();
    }

    public char getCurrQ() {
        return currQ;
    }

    public void setQ(String qStr) {
        Q = new HashSet<>();
        Q.add(q0);
        Q.add(qz);
        String[] arr = qStr.trim().split(" ");
        for (String s : arr){
            Q.add(s.charAt(0));
        }
    }

    public void setA(String aStr) {
        A = new HashSet<>();
        A.add(a0);
        String[] arr = aStr.trim().split(" ");
        for (String s : arr){
            A.add(s.charAt(0));
        }
    }

    public String[] getConfiguration(){
        String[] result = {C, String.valueOf(currA), B};
        return result;
    }

    public String getConfigurationStr(){
        return C + "q" + currQ + currA + B;
    }

    public void setConfiguration(String configuration) {
        for (int i = 0; i < configuration.length(); i++){
            if (configuration.charAt(i) == 'q'){
                C = configuration.substring(0, i);
                currQ = configuration.charAt(i + 1);
                currA = configuration.charAt(i + 2);
                B = configuration.substring(i + 3);
            }
        }
    }

    public String getProgram() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : program.entrySet()){
            sb.append(entry.getKey().trim()).append(" -> ").append(entry.getValue().trim()).append(";\n");
        }
        return sb.toString();
    }

    public void setProgram(String text) {
        program = new HashMap<>();
        String[] arr = text.split(";");
        for (String s : arr){
            String[] tmp = s.split("->");
            if (tmp.length <= 1) continue;
            program.put(tmp[0].trim(),tmp[1].trim());
        }
    }

    public boolean isValidSet(String setStr){
        String[] arr = setStr.trim().split(" ");
        for (String s : arr){
            if (s.length() > 1) return false;
        }
        return true;
    }

    public boolean isValidConfig(String config){
        boolean hasQ = false;
        for (int i = 1; i < config.length(); i++){
            if (config.charAt(i) == 'q') hasQ = true;
            else if (config.charAt(i - 1) == 'q'){
                if (!Q.contains(config.charAt(i))) return false;
            }
            else {
                if (!A.contains(config.charAt(i))) return false;
            }
        }
        return hasQ;
    }

    public boolean isValidRules(String rules){
        String[] rulesArr = rules.split(";");
        for (String s : rulesArr){
            if (!isValidRule(s.trim())) return false;
        }
        return true;
    }

    private boolean isValidRule(String rule) {
        String[] ruleArr = rule.split("->");

        if (ruleArr.length != 2) return false;

        String first = ruleArr[0].trim();

        if (first.length() != 3) return false;
        if (first.charAt(0) != 'q' || !Q.contains(first.charAt(1)) || !A.contains(first.charAt(2))) return false;

        String second = ruleArr[1].trim();

        if (second.length() != 4) return false;
        System.out.println("hi");
       // if (second.charAt(3) == 'R' || second.charAt(3) == 'L' || second.charAt(3) == 'N') System.out.println("waat");
        if (second.charAt(0) != 'q' || !Q.contains(second.charAt(1)) || !A.contains(second.charAt(2)) ||
                !(second.charAt(3) == 'R' || second.charAt(3) == 'L' || second.charAt(3) == 'N')) return false;
        System.out.println("bye");

        return true;
    }
}
