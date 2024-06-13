package com.example.to_do_app_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class DeveloperInfoActivity extends AppCompatActivity {

    private ImageView imgDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);

        imgDp = findViewById(R.id.img_dp);
        ImageView imgBack = findViewById(R.id.img_back);
        ImageView imgEdit = findViewById(R.id.img_edit);

        imgBack.setOnClickListener(v -> {
            Intent intent = new Intent(DeveloperInfoActivity.this, ToDoActivity.class);
            startActivity(intent);
        });

        imgEdit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        findViewById(R.id.btn_exit).setOnClickListener(v -> {
            Intent intent = new Intent(DeveloperInfoActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        Bitmap resizedBitmap = getResizedCircularBitmap(bitmap, 114, 114);
                        imgDp.setImageBitmap(resizedBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private Bitmap getResizedCircularBitmap(Bitmap bitmap, int width, int height) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return getCircularBitmap(resizedBitmap);
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int diameter = Math.min(width, height);
        int radius = diameter / 2;

        Bitmap outputBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(outputBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        path.addCircle(
                (float) diameter / 2,
                (float) diameter / 2,
                57, // 57px radius
                Path.Direction.CCW
        );

        canvas.clipPath(path);
        canvas.drawBitmap(
                bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect(0, 0, diameter, diameter),
                paint
        );

        return outputBitmap;
    }
}
