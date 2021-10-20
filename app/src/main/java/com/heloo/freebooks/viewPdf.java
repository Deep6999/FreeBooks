package com.heloo.freebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class viewPdf extends AppCompatActivity {
Intent intent;
PDFView pdfView;
TextView totalpage;
String pdf,image,from;
Switch switch1;
ProgressDialog progressDialog;
FirebaseFirestore firebaseFirestore;
int mCurrentPage = 0,total;
FirebaseAuth firebaseAuth;
String booknameint;
Boolean switchState=false;
DocumentReference documentReference;
CollectionReference collectionReference;
    HashMap<String, Object> hashMap1 = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        getSupportActionBar().hide();
        switch1 = findViewById(R.id.switch1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        intent = getIntent();
        totalpage = findViewById(R.id.totalpage);
        pdfView =findViewById(R.id.pdfView);
        image = intent.getStringExtra("imageofthebook");
        from = intent.getStringExtra("from");
        firebaseAuth = FirebaseAuth.getInstance();
        pdf = intent.getStringExtra("pdf");
        firebaseFirestore = FirebaseFirestore.getInstance();
       // String des = intent.getStringExtra("Description");
        booknameint= intent.getStringExtra("bookname");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading....");
        progressDialog.show();

         firebaseFirestore.collection("Current").document(firebaseAuth.getCurrentUser().getUid()).collection("pages").document(booknameint).
         get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot snapshot) {
                 if (snapshot.exists()){
                     HashMap<String, Object> m = new HashMap<>();
                    m.put("page", snapshot.getData().get("page"));
                    mCurrentPage = Integer.valueOf(m.get("page").toString());
                 }
                 else{
                     mCurrentPage = 0;
                 }
             }
         });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadfile(pdf);
            }
        },500);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadfile(pdf);
                        switchState = switch1.isChecked();
                    }
                },500);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void loadfile(String pdf) {
        FileLoader.with(this)
                .load(pdf) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("Freebook", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        File loadedFile = response.getBody();
                        pdfView.fromFile(loadedFile)  .enableSwipe(true)
                    .swipeHorizontal(true)
                    .defaultPage(mCurrentPage)
                    .enableDoubletap(true)
                    .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                    .scrollHandle(null)
                    .nightMode(switchState)
                    .enableAntialiasing(true)
                    .spacing(5)
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages) {
                        pdfView.fitToWidth(mCurrentPage);
                    }

//                    @Override
//                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
//                    pdfView.fitToWidth(mCurrentPage);
//                //    Toast.makeText(viewPdf.this, ""+mCurrentPage, Toast.LENGTH_SHORT).show();
//                }
            }).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                 totalpage.setVisibility(View.VISIBLE);
                 total = nbPages;
                  totalpage.setText("Total Pages:-"+nbPages);
                  progressDialog.dismiss();
                }
            }).onPageChange(new OnPageChangeListener() {
                @Override
                public void onPageChanged(final int page, int pageCount) {
                    mCurrentPage = page;

                }
            }).scrollHandle(new DefaultScrollHandle(viewPdf.this,true)).enableAntialiasing(true).load();

                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(viewPdf.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
//    private class pdfdownload extends AsyncTask<String ,Void , InputStream>{
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            InputStream inputStream = null;
//            try {
//                URL url = new URL(strings[0]);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                if (httpURLConnection.getResponseCode() == 200) {
//                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return inputStream;
//        }protected void onProgressUpdate(Integer... progress) {
//        }
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            pdfView.fromStream(inputStream)// all pages are displayed by default
//                    .enableSwipe(true) // allows to block changing pages using swipe
//                    .swipeHorizontal(true)
//                    .defaultPage(mCurrentPage)
//                    .enableDoubletap(true)
//                    .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
//                    .scrollHandle(null)
//                    .enableAntialiasing(true)
//                    .spacing(2)
//                .onRender(new OnRenderListener() {
//                @Override
//                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
//                    pdfView.fitToWidth(mCurrentPage);
//                //    Toast.makeText(viewPdf.this, ""+mCurrentPage, Toast.LENGTH_SHORT).show();
//                }
//            }).onLoad(new OnLoadCompleteListener() {
//                @Override
//                public void loadComplete(int nbPages) {
//                  totalpage.setVisibility(View.VISIBLE);
//                  total = nbPages;
//                   totalpage.setText("Total Pages:-"+nbPages);
//                   progressDialog.dismiss();
//                }
//            }).onPageChange(new OnPageChangeListener() {
//                @Override
//                public void onPageChanged(final int page, int pageCount) {
//                    mCurrentPage = page;
//
//                }
//            }).scrollHandle(new DefaultScrollHandle(viewPdf.this,true)).enableAntialiasing(true).load();
//        }
//    }

    @Override
    protected void onStop() {
        String des = intent.getStringExtra("Description");
        String Author = intent.getStringExtra("Author");
        if (mCurrentPage+1 != total) {
            model model= new model(booknameint,image,pdf,from,mCurrentPage,Author,des);
            firebaseFirestore.collection("Current").document(firebaseAuth.getCurrentUser().getUid()).collection("pages").document(booknameint).set(model);
        }else {
            firebaseFirestore.collection("Current").document(firebaseAuth.getCurrentUser().getUid()).collection("pages").document(booknameint).delete();
            try {
                FileLoader.deleteWith(this).fromDirectory("Freebook", FileLoader.DIR_INTERNAL).deleteFiles(pdf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onStop();
    }

}