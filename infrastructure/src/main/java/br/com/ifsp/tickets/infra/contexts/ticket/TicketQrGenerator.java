package br.com.ifsp.tickets.infra.contexts.ticket;

import br.com.ifsp.tickets.app.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.domain.shared.exceptions.NoStacktraceException;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class TicketQrGenerator implements ITicketQRGenerator {

    private final String feBaseUrl;

    public TicketQrGenerator(@Value("${fe-ticket-check-url}") String feBaseUrl) {
        this.feBaseUrl = feBaseUrl;
    }

    @Override
    public byte[] generateQRCodeToBase64(TicketID ticketID) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            final String qrCodeData = feBaseUrl.formatted(ticketID.getValue().toString());
            final QRCodeWriter qrCodeWriter = new QRCodeWriter();
            final BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 500, 500);

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (WriterException | IOException e) {
            throw new NoStacktraceException(e.getMessage(), e);
        }

        return outputStream.toByteArray();
    }
}
