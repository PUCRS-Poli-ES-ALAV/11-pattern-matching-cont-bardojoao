import java.util.Random;

public class Knut {
    public static void main(String args[]) {
        test();
    }

    static Random generator = new Random();
    static int instruction = 0;
    static int iteraction = 0;

    public static void test() {
        int[] power = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000 };
        String toMatch = "111";
        for (int pow : power) {
            String test = generateText(((5 * pow)));
            System.out.println("Tamanho da palavra :" + test.length());
            long startTime = System.nanoTime();
            KMPSearch(toMatch, test);
            double estimatedTime = (double) (System.nanoTime() - startTime) / 1_000_000_000.0;
            System.out.println("Demorou " + estimatedTime + " segundos");
            System.out.println("Qtd de instrucoes foi " + instruction);
            System.out.println("Qtd de iteracoes foi " + iteraction);
            System.out.println("\n\n");
            clearAll();
        }
    }

    static void KMPSearch(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        addInstruction(6);
        // cria lps[] que vai guardar o maior
        // valor prefixo sufixo para o padrão
        int lps[] = new int[M];
        int j = 0; // index for pat[]
        addInstruction(4);
        // Calcula lps[]
        computeLPSArray(pat, M, lps);
        addInstruction(1);
        int i = 0; // index for txt[]
        addInstruction(1);
        while (i < N) {
            addInstruction(1);
            addIteraction(1);
            addInstruction(3);
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
                addInstruction(4);
            }
            addInstruction(1);
            if (j == M) {
                System.out.println("Found pattern " + "at index " + (i - j));
                j = lps[j - 1];
                addInstruction(2);
            }

            // mismatch após j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                // Não faz match dos caracteres lps[0..lps[j-1]],
                // não é necesssário, eles combinarão
                addInstruction(2);
                addInstruction(1);
                if (j != 0) {

                    j = lps[j - 1];
                    addInstruction(2);
                } else {
                    i = i + 1;
                    addInstruction(2);
                }
            }
            addInstruction(5);
        }
        addInstruction(1);
    }

    static void computeLPSArray(String pat, int M, int lps[]) {
        // tamanho do maior prefixo sufixo anterior
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0
        addInstruction(4);

        // loop calcula lps[i] for i = 1 to M-1
        while (i < M) {
            addInstruction(1);
            addIteraction(1);
            addInstruction(3);
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
                addInstruction(6);
            } else // (pat[i] != pat[len])
            {
                addInstruction(1);
                if (len != 0) {
                    len = lps[len - 1];
                    addInstruction(2);
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                    addInstruction(4);
                }
            }
        }
    }

    public static String generateText(int minSize) {

        String result = new String("111");
        while (result.length() < minSize)
            result = "0" + result + "0";

        return result;
    }

    public static void clearAll() {
        instruction = 0;
        iteraction = 0;
    }

    public static void addInstruction(int value) {
        instruction += value;
    }

    public static void addIteraction(int value) {
        iteraction += value;
    }
}