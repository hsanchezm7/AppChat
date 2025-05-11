package umu.tds.model;

import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class ExportadorMensajes {

	private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
	private static final Font INFO_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font CONTACT_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,
			new BaseColor(0, 102, 204));
	private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	private static final BaseColor HEADER_COLOR = new BaseColor(66, 133, 244);
	private static final BaseColor ROW_COLOR_1 = new BaseColor(240, 248, 255);
	private static final BaseColor ROW_COLOR_2 = BaseColor.WHITE;
	private static final Font FOOTER_FONT = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);

	private static final String FOOTER_MESSAGE = "Documento generado automáticamente por AppChat con iText.";

	public static boolean generarInformeMensajesPdf(Map<String, List<Mensaje>> conversaciones, String usuarioNombre,
			int usuarioId, String rutaDestino) {

		try {
			Document document = new Document(PageSize.A4);
			document.addAuthor("AppChat");
			document.addCreationDate();
			document.setMargins(50, 50, 50, 50);

			PdfWriter.getInstance(document, new FileOutputStream(rutaDestino));
			document.open();

			agregarLogo(document);
			agregarTitulo(document);
			agregarInfoGeneracion(document, usuarioNombre, usuarioId);
			agregarResumen(document, conversaciones);
			agregarFooter(document);

			document.close();
			System.out.println("PDF generado exitosamente en: " + rutaDestino);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void agregarLogo(Document document) {
		try {
			URL logoUrl = ExportadorMensajes.class.getResource("/umu/tds/resources/logo256x256.png");
			if (logoUrl != null) {
				Image logo = Image.getInstance(logoUrl);
				logo.scaleToFit(150, 150);
				logo.setAlignment(Element.ALIGN_CENTER);
				document.add(logo);
			}
		} catch (Exception e) {
			System.out.println("No se pudo cargar el logo: " + e.getMessage());
		}
	}

	private static void agregarTitulo(Document document) throws DocumentException {
		Paragraph title = new Paragraph("INFORME DE CONVERSACIONES", TITLE_FONT);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(10);
		document.add(title);
	}

	private static void agregarInfoGeneracion(Document document, String usuarioNombre, int usuarioId)
			throws DocumentException {
		String fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		Paragraph dateInfo = new Paragraph("Fecha de generación: " + fechaHora, INFO_FONT);
		dateInfo.setAlignment(Element.ALIGN_RIGHT);
		document.add(dateInfo);

		Paragraph userInfo = new Paragraph("Generado por: " + usuarioNombre + " (ID: " + usuarioId + ")", INFO_FONT);
		userInfo.setAlignment(Element.ALIGN_RIGHT);
		userInfo.setSpacingAfter(20);
		document.add(userInfo);

		LineSeparator ls = new LineSeparator();
		ls.setLineColor(BaseColor.LIGHT_GRAY);
		document.add(new Chunk(ls));
		document.add(new Paragraph(" "));
	}

	private static void agregarResumen(Document document, Map<String, List<Mensaje>> conversaciones)
			throws DocumentException {
		document.add(new Paragraph("RESUMEN DE MENSAJES", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
		document.add(new Paragraph("Total de contactos: " + conversaciones.size(), INFO_FONT));
		document.add(new Paragraph(" "));

		for (Map.Entry<String, List<Mensaje>> entry : conversaciones.entrySet()) {
			String nombreContacto = entry.getKey();
			List<Mensaje> mensajes = entry.getValue();

			Paragraph contactHeader = new Paragraph("Contacto: " + nombreContacto, CONTACT_FONT);
			contactHeader.setSpacingBefore(15);
			contactHeader.setSpacingAfter(5);
			document.add(contactHeader);

			if (mensajes == null || mensajes.isEmpty()) {
				Paragraph sinMensajes = new Paragraph("No hay mensajes con este contacto.", INFO_FONT);
				sinMensajes.setSpacingAfter(10);
				document.add(sinMensajes);
			} else {
				PdfPTable table = crearTablaMensajes(mensajes);
				document.add(table);
			}

			document.add(new Paragraph(" "));
		}
	}

	private static PdfPTable crearTablaMensajes(List<Mensaje> mensajes) throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 0.1f, 0.2f, 0.3f, 0.4f });

		// Cabeceras
		Stream.of("ID", "Emisor", "Fecha y Hora", "Mensaje").forEach(col -> {
			PdfPCell header = new PdfPCell(new Phrase(col, HEADER_FONT));
			header.setBackgroundColor(HEADER_COLOR);
			header.setPadding(5);
			table.addCell(header);
		});

		boolean alternate = false;
		for (Mensaje m : mensajes) {
			BaseColor bgColor = alternate ? ROW_COLOR_1 : ROW_COLOR_2;

			table.addCell(crearCelda(String.valueOf(m.getId()), bgColor));
			table.addCell(crearCelda(m.getEmisor().getName(), bgColor));
			table.addCell(crearCelda(m.getFechaHora().toString(), bgColor));
			table.addCell(crearCelda(m.getTexto(), bgColor));

			alternate = !alternate;
		}

		return table;
	}

	private static PdfPCell crearCelda(String contenido, BaseColor background) {
		PdfPCell cell = new PdfPCell(new Phrase(contenido));
		cell.setBackgroundColor(background);
		cell.setPadding(4);
		return cell;
	}

	private static void agregarFooter(Document document) throws DocumentException {
		LineSeparator ls = new LineSeparator();
		ls.setLineColor(BaseColor.LIGHT_GRAY);
		document.add(new Chunk(ls));
		document.add(new Paragraph(" "));

		Paragraph footer = new Paragraph(FOOTER_MESSAGE, FOOTER_FONT);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.add(footer);
	}
}
