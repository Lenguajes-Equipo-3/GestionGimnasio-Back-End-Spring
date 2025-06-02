package Lenguajes.Proyecto1.service;

import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ItemRutinaEjercicio;
import Lenguajes.Proyecto1.domain.Rutina;
import Lenguajes.Proyecto1.data.RutinaData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteRutinaService {

    @Autowired
    private RutinaData rutinaData;

  public void exportarRutinaClientePDF(int idCliente, OutputStream outputStream) {
    try {
        Rutina rutina = rutinaData.findRutinaByClienteId(idCliente);
        if (rutina == null) {
            throw new RuntimeException("No se encontró rutina para el cliente con ID: " + idCliente);
        }

        List<ItemRutinaEjercicio> ejercicios = rutina.getEjercicios();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombreCliente", rutina.getCliente().getNombreCliente() + " " + rutina.getCliente().getApellidosCliente());
        parametros.put("telefono", rutina.getCliente().getTelefono());
        parametros.put("entrenador", rutina.getEmpleado().getNombreEmpleado());
        parametros.put("fechaCreacion", rutina.getFechaCreacion().toString());
        parametros.put("fechaRenovacion", rutina.getFechaRenovacion().toString());
        parametros.put("lesiones", rutina.getLesiones());
        parametros.put("enfermedades", rutina.getEnfermedades());

        // Lee y compila el archivo jrxml
        InputStream inputStream = getClass().getResourceAsStream("/reportes/rutina_cliente.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Usa directamente la lista como datasource principal
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ejercicios);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

    } catch (Exception e) {
        e.printStackTrace(); // Agregá esto para ver el error real en consola
        throw new RuntimeException("Error al generar el PDF de rutina", e);
    }
}

}
