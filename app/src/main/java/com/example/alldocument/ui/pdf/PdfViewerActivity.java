package com.example.alldocument.ui.pdf;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.ui.document.DocumentFragment;
import com.example.alldocument.ui.office.ControlOfficeFragment;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfViewerActivity extends AppCompatActivity implements ControlOfficeFragment.GoToPageListener {
    public final static String PDF_DATA_MODEL = "PDF_DATA_MODEL";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    FileModel filePdf;
    private DocumentFragment documentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        ButterKnife.bind(this);
        filePdf = (FileModel) getIntent().getExtras().getSerializable(PDF_DATA_MODEL);
        initView();
    }

    private void initView() {
        setupToolbar();
        setUpdateViewPDF();
    }

    private void setUpdateViewPDF() {
        pdfView.fromFile(new File(filePdf.getPath())).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {

            }
        }).onError(new OnErrorListener() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(
                        PdfViewerActivity.this,
                        getString(R.string.load_file_error),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }).onPageError(new OnPageErrorListener() {
            @Override
            public void onPageError(int page, Throwable t) {
                Toast.makeText(
                        PdfViewerActivity.this,
                        getString(R.string.load_file_error),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }).enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Toast.makeText(PdfViewerActivity.this, (page + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
                    }
                })
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .spacing(10)
                .invalidPageColor(Color.GRAY)
                .load();
//        FragmentTransaction fragmentTransaction =
//                getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(
//                R.id.layout_main_pdf,
//                ControlOfficeFragment.newInstance(
//                        filePdf),
//        "ControlOfficeFragment"
//        );
//        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    private void setupToolbar() {
        String name = getIntent().getExtras().getString(filePdf.getName());
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void goToPage(int pageNumber, HomeItemType type) {
        pdfView.jumpTo(pageNumber);
    }
}
