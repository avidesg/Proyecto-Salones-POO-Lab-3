module edu.ufg.aulas.proyectoLaboratorio3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    
    requires java.sql;
    requires mysql.connector.j;

opens edu.ufg.aulas.proyectoLaboratorio3.entidades to org.hibernate.orm.core;
    
    opens edu.ufg.aulas.proyectoLaboratorio3 to javafx.fxml;
    exports edu.ufg.aulas.proyectoLaboratorio3;
}