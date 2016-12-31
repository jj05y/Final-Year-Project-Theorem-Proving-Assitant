package gui.theoremloadsave;

/**
 * Created by joe on 31/12/16.
 */
public class SavableTheorem {

    private String plainTextTheorem;
    private String derivation;
    private boolean isAxiom;

    public SavableTheorem(String plainTextTheorem, String derivation, boolean isAxiom) {
        this.plainTextTheorem = plainTextTheorem;
        this.derivation = derivation;
        this.isAxiom = isAxiom;
    }

    public String getPlainTextTheorem() {
        return plainTextTheorem;
    }

    public String getDerivation() {
        return derivation;
    }

    public boolean isAxiom() {
        return isAxiom;
    }
}
