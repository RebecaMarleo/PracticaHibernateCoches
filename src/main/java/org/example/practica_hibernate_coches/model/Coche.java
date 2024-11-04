package org.example.practica_hibernate_coches.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;

/*el nombre de la entidad se establece por defecto como el
nombre de la clase si no se establece uno manualmente*/
@Entity
@Table(name = "Coches")
public class Coche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String tipo;

    public Coche() {

    }

    public Coche(String matricula, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public Coche(int id, String matricula, String marca, String modelo, String tipo) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Coche: id - " + id + ", matrícula - " + matricula + ", marca - " + marca + ", modelo - " + modelo + ", tipo - " + tipo;
    }

    public ObservableValue<String> matriculaProperty() {
        StringProperty propMatricula = new SimpleStringProperty(matricula);
        return propMatricula;
    }

    public ObservableValue<String> marcaProperty() {
        StringProperty propMarca = new SimpleStringProperty(marca);
        return propMarca;
    }

    public ObservableValue<String> modeloProperty() {
        StringProperty propModelo = new SimpleStringProperty(modelo);
        return propModelo;
    }

    public ObservableValue<String> tipoProperty() {
        StringProperty propTipo = new SimpleStringProperty(tipo);
        return propTipo;
    }
}
