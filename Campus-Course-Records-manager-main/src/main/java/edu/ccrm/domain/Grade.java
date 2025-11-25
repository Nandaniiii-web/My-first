package edu.ccrm.domain;

public enum Grade {
    S(10), A(9), B(8), C(7), D(6), E(5), F(0);

    private final int points;
    Grade(int p) { this.points = p; }
    public int getPoints() { return points; }

    public static Grade fromMarks(double marks) {
        if (Double.isNaN(marks)) return F;
        if (marks >= 90) return S;
        if (marks >= 80) return A;
        if (marks >= 70) return B;
        if (marks >= 60) return C;
        if (marks >= 50) return D;
        if (marks >= 40) return E;
        return F;
    }
}
