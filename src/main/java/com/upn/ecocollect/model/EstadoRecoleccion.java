package com.upn.ecocollect.model;

public enum EstadoRecoleccion {
    PENDIENTE,  // Solicitud recién creada
    ASIGNADA,   // Asignada a un recolector/ruta
    EN_RUTA,    // Recolector en camino
    COMPLETADA, // Servicio finalizado y pago realizado
    CANCELADA
}