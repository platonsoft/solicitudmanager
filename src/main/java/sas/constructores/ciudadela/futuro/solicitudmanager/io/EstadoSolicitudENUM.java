package sas.constructores.ciudadela.futuro.solicitudmanager.io;

public enum EstadoSolicitudENUM {
    PENDIENTE("pendiente"),
    EN_PROGRESO("en progreso"),
    TERMINADA("terminada");

    private final String estado;

    EstadoSolicitudENUM(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
