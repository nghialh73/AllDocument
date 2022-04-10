package com.example.alldocument.ui.office;

import static com.example.alldocument.ui.document.DocumentActivity.HOME_TYPE_ITEM_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.BuildConfig;
import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.ui.ViewModelFactory;
import com.example.alldocument.ui.document.DocumentAdapter;
import com.example.alldocument.ui.document.DocumentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlOfficeFragment extends Fragment implements View.OnClickListener {

    private static final String DATA_FILE_MODEL = "DATA_FILE_MODEL";
    private FileModel fileModel;
    private GoToPageListener mGotoPage;
    @BindView(R.id.control_floating_button_goto_page)
    FloatingActionButton floatingBtnGoPage;
    @BindView(R.id.control_floating_text_goto_page)
    AppCompatTextView textGoPage;
    @BindView(R.id.control_floating_button)
    FloatingActionButton floatingButton;
//    @BindView(R.id.control_floating_button_favourite)
//    FloatingActionButton floatingButtonFavorite;
    @BindView(R.id.control_floating_button_share)
    FloatingActionButton floatingButtonShare;
//    @BindView(R.id.control_floating_button_screen_short)
//    FloatingActionButton floatingButtonScreenShort;
    @BindView(R.id.control_layout_control)
    ConstraintLayout layoutControl;

    public static ControlOfficeFragment newInstance(FileModel fileModel) {
        ControlOfficeFragment fragment = new ControlOfficeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_FILE_MODEL, fileModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mGotoPage = (GoToPageListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_office, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        fileModel = (FileModel) getArguments().getSerializable(DATA_FILE_MODEL);
    }

    private void initView() {
        if (fileModel.getType() == HomeItemType.PDF || fileModel.getType() == HomeItemType.WORD || fileModel.getType() == HomeItemType.POWER_POINT || fileModel.getType() == HomeItemType.TEXT) {
            floatingBtnGoPage.setVisibility(View.VISIBLE);
            textGoPage.setVisibility(View.VISIBLE);
        } else {
            floatingBtnGoPage.setVisibility(View.GONE);
            textGoPage.setVisibility(View.GONE);
        }
        floatingButton.setOnClickListener(this);
        //floatingButtonFavorite.setOnClickListener(this);
        floatingButtonShare.setOnClickListener(this);
        //floatingButtonScreenShort.setOnClickListener(this);
        floatingBtnGoPage.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.control_floating_button:
                if (layoutControl.getVisibility() == View.VISIBLE) {
                    layoutControl.setVisibility(View.GONE);
                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_add));
                } else {
                    layoutControl.setVisibility(View.VISIBLE);
                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_close));
                }
                break;
//            case R.id.control_floating_button_favourite:
//                if (layoutControl.getVisibility() == View.VISIBLE) {
//                    layoutControl.setVisibility(View.GONE);
//                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_add));
//                } else {
//                    layoutControl.setVisibility(View.VISIBLE);
//                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_close));
//                }
//                fileModel.setFavorite(fileModel.getFavorite() == 0 ? 1 : 0);
//                if (fileModel.getFavorite() == 1)
//                    floatingButtonFavorite.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_favorite));
//                else
//                    floatingButtonFavorite.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_favorite_none));
//                break;
//            case R.id.control_floating_button_screen_short:
//                if (layoutControl.getVisibility() == View.VISIBLE) {
//                    layoutControl.setVisibility(View.GONE);
//                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_add));
//                } else {
//                    layoutControl.setVisibility(View.VISIBLE);
//                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_close));
//                }
//
//                floatingButton.setVisibility(View.GONE);
//                Bitmap imageBitmap = takeScreenshot();
//                if (imageBitmap == null) {
//                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                showCropView(imageBitmap);
//                floatingButton.setVisibility(View.VISIBLE);
//                break;
            case R.id.control_floating_button_share:
                if (layoutControl.getVisibility() == View.VISIBLE) {
                    layoutControl.setVisibility(View.GONE);
                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_add));
                } else {
                    layoutControl.setVisibility(View.VISIBLE);
                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_close));
                }
                try {
                    File file = new File(fileModel.getPath());
                    if (file.exists()) {
                        Uri uri = FileProvider.getUriForFile(requireActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.control_floating_button_goto_page:
                if (layoutControl.getVisibility() == View.VISIBLE) {
                    layoutControl.setVisibility(View.GONE);
                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_add));
                } else {
                    layoutControl.setVisibility(View.VISIBLE);
                    floatingButton.setImageDrawable(requireActivity().getResources().getDrawable(R.drawable.ic_close));
                }
                showDialog();
                break;
        }
    }

    private void showCropView(Bitmap imageBitmap) {
        Uri uri = getImageUri(requireActivity(), imageBitmap);
        CropImage.activity(uri)
                .start(requireActivity());
    }

    private Uri getImageUri(
            Context inContext,
            Bitmap inImage
    ) {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".png";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(),
                inImage,
                imageName,
                null
        );
        return Uri.parse(path);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(requireContext().getString(R.string.goto_page));
        EditText input = new EditText(requireContext());
        input.setHint("Page Number");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton(requireContext().getString(R.string.goto_page), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mText = input.getText().toString();
                mGotoPage.goToPage(Integer.parseInt(mText) - 1, fileModel.getType());
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton(requireContext().getString(R.string.title_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private Bitmap takeScreenshotOfView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            return null;
        }
        view.draw(canvas);
        return bitmap;
    }

    private Bitmap takeScreenshot() {
        Bitmap bitmap = null;
        Date now = new Date();
        String date = DateFormat.format("yyyy-MM-dd_hh:mm:ss", now).toString();
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath =
                    Environment.getExternalStorageDirectory().toString().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = requireActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            bitmap =
                    Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }

        return bitmap;
    }

    public interface GoToPageListener {
        public void goToPage(int pageNumber, HomeItemType type);
    }
}
