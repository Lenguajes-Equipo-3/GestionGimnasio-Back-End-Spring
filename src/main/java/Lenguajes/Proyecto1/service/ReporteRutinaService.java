package Lenguajes.Proyecto1.service;

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

public void exportarRutinasClientePDF(int idCliente, OutputStream outputStream) {
    try {
        List<Rutina> rutinas = rutinaData.findRutinasByClienteId(idCliente);
        if (rutinas == null || rutinas.isEmpty()) {
            throw new RuntimeException("No se encontraron rutinas para el cliente con ID: " + idCliente);
        }

        
        
        System.out.println(rutinas.size() );
        // Compilar subreportes
        InputStream ejerciciosStream = getClass().getResourceAsStream("/reportes/ejercicios_rutina.jrxml");
        JasperReport subreporteEjercicios = JasperCompileManager.compileReport(ejerciciosStream);

        InputStream rutinaStream = getClass().getResourceAsStream("/reportes/rutina_cliente.jrxml");
        JasperReport subreporteRutina = JasperCompileManager.compileReport(rutinaStream);

        InputStream listaStream = getClass().getResourceAsStream("/reportes/rutina_cliente_lista.jrxml");
        JasperReport reportePrincipal = JasperCompileManager.compileReport(listaStream);

        // Parámetros que se pasan a cada uso del subreporte rutina_cliente.jrxml
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SUBREPORT", subreporteRutina);
        parametros.put("EJERCICIOS_SUBREPORT", subreporteEjercicios);
        

        // Llenar el reporte con la lista de rutinas
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rutinas);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePrincipal, parametros, dataSource);

        // Exportar a PDF
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error al generar el PDF de rutinas del cliente", e);
    }
}


}
