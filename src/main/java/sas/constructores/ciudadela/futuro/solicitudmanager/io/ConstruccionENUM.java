package sas.constructores.ciudadela.futuro.solicitudmanager.io;

public enum ConstruccionENUM {
    CASA(100, 50, 90, 20, 100, 3),
    LAGO(50, 60, 80, 10, 20, 2),
    CANCHA_FUTBOL(20, 20, 20, 20, 20, 1),
    EDIFICIO(200, 100, 180, 40, 20, 6),
    GIMNACIO(50, 25, 45, 10, 50, 2);

    private final Integer ce;
    private final Integer gr;
    private final Integer ar;
    private final Integer ma;
    private final Integer ad;
    private final Integer duracion;


    ConstruccionENUM(Integer ce, Integer gr, Integer ar, Integer ma, Integer ad, Integer duracion) {
        this.ce = ce;
        this.gr = gr;
        this.ar = ar;
        this.ma = ma;
        this.ad = ad;
        this.duracion = duracion;
    }

    public Integer getCe() {
        return ce;
    }

    public Integer getGr() {
        return gr;
    }

    public Integer getAr() {
        return ar;
    }

    public Integer getMa() {
        return ma;
    }

    public Integer getAd() {
        return ad;
    }

    public Integer getDuracion() {
        return duracion;
    }
}
