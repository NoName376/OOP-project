package academic;

import java.io.Serializable;

public class Mark implements Serializable {
    public Mark() {}

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    public void setMark(AttestationType type, double value) {
        switch (type) {
            case FIRST_ATT: firstAttestation = value; break;
            case SECOND_ATT: secondAttestation = value; break;
            case FINAL_EXAM: finalExam = value; break;
        }
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public double getFirstAttestation() { return firstAttestation; }
    public void setFirstAttestation(double firstAttestation) { this.firstAttestation = firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public void setSecondAttestation(double secondAttestation) { this.secondAttestation = secondAttestation; }
    public double getFinalExam() { return finalExam; }
    public void setFinalExam(double finalExam) { this.finalExam = finalExam; }

    @Override
    public String toString() {
        return "Mark [Total=" + getTotal() + "]";
    }

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
}
