package com.example.warehousemanagement;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.dialog.CustomDialog;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.ReceiptDetail;
import com.example.warehousemanagement.model.Warehouse;
import com.example.warehousemanagement.utilities.DateHandler;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;


public class ReceiptViewActivity extends AppCompatActivity implements CustomDialog.Listener {
    TextView tvTitle;
    Receipt receipt;
    ReceiptViewFragment receiptViewFragment;
    ImageButton btnPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_view_activity);
        setControl();
        setEvent();

        tvTitle.setText("Chi tiết");

        receipt = (Receipt) this.getIntent().getSerializableExtra("receipt");
        ReceiptViewFragment receiptViewFragment = new ReceiptViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, receiptViewFragment);
        fragmentTransaction.commit();
    }

    private void setEvent() {
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnPdfClicked(view);
            }
        });
    }

    private void setControl() {
        tvTitle = findViewById(R.id.tvTitle);
        btnPdf = findViewById(R.id.btnPdf);
    }

    private void handleBtnPdfClicked(View view) {
        createPdf();
    }

    private void createPdf() {
        WarehouseDao warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));
        ProductDao productDao = new ProductDao(DatabaseHelper.getInstance(this));

        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        String name = "Receipt no " + Integer.toString(receipt.getId()) + ".pdf";
        File file = new File(path, name);
        if (file.exists()) {
            file.delete();
        }

        try {
            String ROBOTO = "src/main/resources/fonts/Roboto-Regular.ttf";
            PdfFont font;
            AssetManager am = this.getAssets();
            try (InputStream inStream = am.open("roboto_regular.ttf")) {
                byte[] fontData = IOUtils.toByteArray(inStream);
                font = PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H, true);
            }

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            //URL font_path = Thread.currentThread().getContextClassLoader().getResource("roboto_regular");
            //FontProgram fontProgram = FontProgramFactory.createFont("arialuni.tff");
            //PdfFont font = PdfFontFactory.createFont();
            //byte[] fontContents = IOUtils.toByteArray(getClass().getResourceAsStream("roboto_regular.t"));

            //FontProgram fontProgram = FontProgramFactory.createFont(fontContents);
            //PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA, PdfEncodings.IDENTITY_H);
            //document.setFont(PdfFontFactory.createFont("assets/subFolder/fontName.TTF", PdfEncodings.IDENTITY_H, true));
            document.setFont(font);

            document.add(new Paragraph("Thông tin nhập kho").setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(18).setBold());
            document.add(new Paragraph("\n"));

            float columnWidth[] = {140, 140, 140, 140};
            Table table = new Table(columnWidth);

            Warehouse warehouse = warehouseDao.getOne(receipt.getWarehouseId());
            table.addCell(new Cell().add(new Paragraph("Mã kho").setBold()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(receipt.getWarehouseId())).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Tên kho").setBold()).setBold().setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(warehouse.getName())).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph("Số phiếu").setBold()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(receipt.getId()))).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Ngày lập").setBold()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(DateHandler.convertLocalDateToString(receipt.getDate()))).setBorder(Border.NO_BORDER));

            document.add(table);
            document.add(new Paragraph("\n"));

            float columnWidth2[] = {60, 110, 110, 110, 110, 110};
            Table table2 = new Table(columnWidth2);

            table2.addCell(new Cell().add(new Paragraph("STT").setBold()));
            table2.addCell(new Cell().add(new Paragraph("Mã vật tư").setBold()));
            table2.addCell(new Cell().add(new Paragraph("Tên vật tư").setBold()));
            table2.addCell(new Cell().add(new Paragraph("Xuất xứ").setBold()));
            table2.addCell(new Cell().add(new Paragraph("Đơn vị tính").setBold()));
            table2.addCell(new Cell().add(new Paragraph("Số lượng nhập").setBold()));

            for (ReceiptDetail receiptDetail : receipt.getReceiptDetails()) {
                table2.addCell(new Cell().add(new Paragraph(Integer.toString(receipt.getReceiptDetails().indexOf(receiptDetail) + 1))));
                table2.addCell(new Cell().add(new Paragraph(receiptDetail.getProductId())));

                Product product = productDao.getOne(receiptDetail.getProductId());

                table2.addCell(new Cell().add(new Paragraph(product.getName())));
                table2.addCell(new Cell().add(new Paragraph(product.getOrigin())));
                table2.addCell(new Cell().add(new Paragraph(receiptDetail.getUnit())));
                table2.addCell(new Cell().add(new Paragraph(Integer.toString(receiptDetail.getQuantity()))));
            }

            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Số loại vật tư:").setBold()).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph(Integer.toString(receipt.getReceiptDetails().size())).setBold()).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            document.add(table2);
            document.close();

            CustomDialog customDialog = new CustomDialog(CustomDialog.Type.NOTIFICATION, "Thành công", "Tạo file PDF thành công", "pdf-success");
            customDialog.show(getSupportFragmentManager(), "");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }


    public Receipt getReceipt() {
        return this.receipt;
    }

    @Override
    public void sendDialogResult(CustomDialog.Result result, String request) {
        if (result == CustomDialog.Result.OK) {
            if (request.equals("pdf-success")) {
            }
        }
    }
}
