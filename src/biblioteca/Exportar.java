
package biblioteca;

import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class Exportar {
         
    private static final Font categoryFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private final String ruta = "/doc_biblio/";

    public void CreatePDF(String nombre_archivo, JTable jtable){
        try {
            Document document = new Document();
            String a = ruta+nombre_archivo+".pdf";
            try {
                File path = new File(a);
                PdfWriter.getInstance(document, new FileOutputStream(path));
 
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            
            int x = nombre_archivo.indexOf("(");
            int y = nombre_archivo.indexOf("_");
            String title = nombre_archivo.substring(0,x);
            String fecha = nombre_archivo.substring(x+1,y);
            String hora = nombre_archivo.substring(y+1,y+3);
            String minutos = nombre_archivo.substring(y+4,y+6);
            
            Paragraph titulo = new Paragraph("",categoryFont);
            titulo.add(title+"\n");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            Paragraph paragraph = new Paragraph("",subcategoryFont);
            paragraph.add("Documento creado el "+fecha+" a las "+ hora+":"+minutos+" hrs.");
            
            Integer numColumns = jtable.getColumnCount();
            Integer numRows = jtable.getRowCount();
            
            PdfPTable table = new PdfPTable(numColumns); 
           
            PdfPCell columnHeader;
                     
            for (int column = 0; column < numColumns; column++) {
                columnHeader = new PdfPCell(new Phrase(jtable.getColumnName(column)));
                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(columnHeader);
            }
            table.setHeaderRows(1);
                     
            for (int row = 0; row < numRows; row++) {
                for (int column = 0; column < numColumns; column++) {
                    table.addCell((String) jtable.getValueAt(row, column));
                }
            }
            
            paragraph.add(table);
            
            document.add(paragraph);
            document.close();
            
            JOptionPane.showMessageDialog(null, "Documento creado con exito");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
 
    }

    public void Abrir(String archivo){
        try{
            File path = new File(ruta+archivo+".pdf");
            Desktop.getDesktop().open(path);
        }catch(IOException e){
            System.out.print("Revisar "+e);
        }
    }
}
