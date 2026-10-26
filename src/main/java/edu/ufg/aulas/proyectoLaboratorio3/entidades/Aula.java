/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ufg.aulas.proyectoLaboratorio3.entidades;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 *
 * @author elect
 */



@Entity
@Table(
    name = "aula",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_aula_nombre_edificio",
            columnNames = {"nombre", "edificio"}
        )
    }
)
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "edificio", nullable = false, length = 100)
    private String edificio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planta_id", nullable = false)
    private Planta planta;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_aula_id", nullable = false)
    private TipoAula tipoAula;

    @Column(name = "responsable", nullable = false, length = 120)
    private String responsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_aula_id", nullable = false)
    private EstadoAula estado;

    public Aula() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public TipoAula getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(TipoAula tipoAula) {
        this.tipoAula = tipoAula;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public EstadoAula getEstado() {
        return estado;
    }

    public void setEstado(EstadoAula estado) {
        this.estado = estado;
    }
}
