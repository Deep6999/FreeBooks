package com.heloo.freebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


public class test extends AppCompatActivity {
    Intent intent;
    public static final String BOOK_NAME = "thesilverchair.epub";
//    TextView io;
//    EpubView epubView;
//    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        intent = getIntent();
////        epubView =  findViewById(R.id.epub_view);
//       final String e = intent.getStringExtra("pdf");

//
//        FileLoader.with(this)
//                .load(e) //2nd parameter is optioal, pass true to force load from network
//                .fromDirectory("Freebook", FileLoader.DIR_INTERNAL)
//                .asFile(new FileRequestListener<File>() {
//                    @Override
//                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//
//                    }
//
//                    @Override
//                    public void onError(FileLoadRequest request, Throwable t) {
//                        Toast.makeText(test.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//        try {
//            EpubReaderComponent epubReader = new EpubReaderComponent(e);
//            Toast.makeText(getApplicationContext(), "ok \n"+e, Toast.LENGTH_SHORT).show();
//            BookEntity bookEntity = epubReader.make(this);
//            List<String> allPage = bookEntity.getPagePathList();
//            String bookName = bookEntity.getName();
//            String authorName = bookEntity.getAuthor();
//            String coverImage = bookEntity.getCoverImage();
//            List<SubBookEntity> allPage1 = bookEntity.getSubBookHref();
//            epubView.setBaseUrl(epubReader.getAbsolutePath());
//            String content = EpubUtil.getHtmlContent(allPage.get(3));
//            epubView.setUp(content);
//            epubView.setFontSize(15);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }












//
//        FileLoader.with(this)
//                .load(e) //2nd parameter is optioal, pass true to force load from network
//                .fromDirectory("Free", FileLoader.DIR_INTERNAL)
//                .asFile(new FileRequestListener<File>() {
//                    @Override
//                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                        File loadedFile = response.getBody();
//                        Reader reader = new Reader();
//                        reader.setMaxContentPerSection(1000); // Max string length for the current page.
//                        reader.setIsIncludingTextContent(true); // Optional, to return the tags-excluded version.
//                        try {
//                            reader.setFullContent(String.valueOf(loadedFile)); // Must call before readSection.
//                        } catch (ReadingException ex) {
//                            ex.printStackTrace();
//                        }
//
//                        BookSection bookSection = null;
//                        try {
//                            bookSection = reader.readSection(3);
//                            String sectionContent = bookSection.getSectionContent(); // Returns content as html.
//                            String sectionTextContent = bookSection.getSectionTextContent(); // Excludes html tags.
//
//
//                        } catch (ReadingException | OutOfPagesException ex) {
//                            ex.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(FileLoadRequest request, Throwable t) {
//                        Toast.makeText(test.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });


//        FolioReader folioReader = FolioReader.get();
//        folioReader.openBook(BOOK_NAME);
    }


}