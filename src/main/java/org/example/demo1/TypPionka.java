package org.example.demo1;

public enum TypPionka {
    RED(1),WHITE(-1);

    final int kierunekRuchu;
    TypPionka(int kierunekRuchu) {
        this.kierunekRuchu = kierunekRuchu;
    }
}
